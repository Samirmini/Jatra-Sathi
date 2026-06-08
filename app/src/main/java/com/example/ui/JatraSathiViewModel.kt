package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.BookingEntity
import com.example.data.JatraSathiRepository
import com.example.data.TransactionEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID

data class JatraSathiUiState(
    val bookings: List<BookingEntity> = emptyList(),
    val activeBooking: BookingEntity? = null,
    val transactions: List<TransactionEntity> = emptyList(),
    val walletBalance: Int = 0,
    val bookingStep: Int = 0, // 0 = Route Setup, 1 = Preference, 2 = Confirmed/Finding/Active
    val pickupLocation: String = "",
    val destinationLocation: String = "",
    val preferredGender: String = "Any",
    val preferredLanguage: String = "Bengali",
    val pickupTime: String = "Now",
    val isSearching: Boolean = false,
    val activeTab: Int = 0, // 0 = Booking, 1 = Active Radar Map, 2 = Trust & Wallet Hub
    val isNidUploaded: Boolean = true, // Pre-verified client for high-fidelity experience
    val isFaceMatchVerified: Boolean = true,
    val isInsuranceActive: Boolean = true,
    val searchProgress: Float = 0f,
    // Custom radar companion location simulation offsets for animated pulse map
    val companionLatitudeOffset: Float = 0.015f,
    val companionLongitudeOffset: Float = 0.015f
)

class JatraSathiViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: JatraSathiRepository
    
    private val _bookingStep = MutableStateFlow(0)
    private val _pickupLocation = MutableStateFlow("")
    private val _destinationLocation = MutableStateFlow("")
    private val _preferredGender = MutableStateFlow("Any")
    private val _preferredLanguage = MutableStateFlow("Bengali")
    private val _pickupTime = MutableStateFlow("Now")
    private val _isSearching = MutableStateFlow(false)
    private val _activeTab = MutableStateFlow(0)
    private val _isNidUploaded = MutableStateFlow(true)
    private val _isFaceMatchVerified = MutableStateFlow(true)
    private val _isInsuranceActive = MutableStateFlow(true)
    private val _searchProgress = MutableStateFlow(0f)
    private val _companionLatitudeOffset = MutableStateFlow(0.015f)
    private val _companionLongitudeOffset = MutableStateFlow(0.015f)

    init {
        val database = AppDatabase.getDatabase(application)
        repository = JatraSathiRepository(database)
        seedLedgerIfEmpty()
        simulateCompanionMovement()
    }

    val uiState: StateFlow<JatraSathiUiState> = combine(
        repository.allBookings,
        repository.activeBooking,
        repository.allTransactions,
        _bookingStep,
        _pickupLocation,
        _destinationLocation,
        _preferredGender,
        _preferredLanguage,
        _pickupTime,
        _isSearching,
        _activeTab,
        _isNidUploaded,
        _isFaceMatchVerified,
        _isInsuranceActive,
        _searchProgress,
        _companionLatitudeOffset,
        _companionLongitudeOffset
    ) { params ->
        val bookings = params[0] as List<BookingEntity>
        val activeBooking = params[1] as BookingEntity?
        val transactions = params[2] as List<TransactionEntity>
        val bookingStep = params[3] as Int
        val pickup = params[4] as String
        val destination = params[5] as String
        val gender = params[6] as String
        val language = params[7] as String
        val time = params[8] as String
        val searching = params[9] as Boolean
        val tab = params[10] as Int
        val nid = params[11] as Boolean
        val face = params[12] as Boolean
        val insurance = params[13] as Boolean
        val progress = params[14] as Float
        val latOffset = params[15] as Float
        val lonOffset = params[16] as Float

        // Calculate dynamic ledger balance
        val balance = transactions.fold(0) { acc, txn ->
            if (txn.type == "DEPOSIT" || txn.type == "RECLAIM" || txn.type == "INSURANCE_BONUS") {
                acc + txn.amountCoins
            } else {
                acc - txn.amountCoins
            }
        }

        JatraSathiUiState(
            bookings = bookings,
            activeBooking = activeBooking,
            transactions = transactions,
            walletBalance = balance,
            bookingStep = bookingStep,
            pickupLocation = pickup,
            destinationLocation = destination,
            preferredGender = gender,
            preferredLanguage = language,
            pickupTime = time,
            isSearching = searching,
            activeTab = tab,
            isNidUploaded = nid,
            isFaceMatchVerified = face,
            isInsuranceActive = insurance,
            searchProgress = progress,
            companionLatitudeOffset = latOffset,
            companionLongitudeOffset = lonOffset
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = JatraSathiUiState()
    )

    private fun seedLedgerIfEmpty() {
        viewModelScope.launch {
            // Check transactions list
            val list = repository.allTransactions.first()
            if (list.isEmpty()) {
                    // Seed original wallet balance
                    repository.insertTransaction(
                        TransactionEntity(
                            type = "DEPOSIT",
                            tokenCode = "TXN-SECURE-ID-8291A",
                            amountCoins = 15000,
                            status = "LEDGER_VALID"
                        )
                    )
                    repository.insertTransaction(
                        TransactionEntity(
                            type = "INSURANCE_BONUS",
                            tokenCode = "TXN-MED-CREDIT-7102B",
                            amountCoins = 500,
                            status = "LEDGER_VALID"
                        )
                    )
                    repository.insertTransaction(
                        TransactionEntity(
                            type = "DEPOSIT",
                            tokenCode = "TXN-VERIFY-REWARD-4039X",
                            amountCoins = 250,
                            status = "LEDGER_VALID"
                        )
                    )
                }
        }
    }

    // Interactive form handlers
    fun updatePickup(location: String) {
        _pickupLocation.value = location
    }

    fun updateDestination(location: String) {
        _destinationLocation.value = location
    }

    fun updateGender(gender: String) {
        _preferredGender.value = gender
    }

    fun updateLanguage(language: String) {
        _preferredLanguage.value = language
    }

    fun updatePickupTime(time: String) {
        _pickupTime.value = time
    }

    fun setBookingStep(step: Int) {
        _bookingStep.value = step
    }

    fun setTab(tab: Int) {
        _activeTab.value = tab
    }

    fun setInsuranceActive(active: Boolean) {
        _isInsuranceActive.value = active
    }

    fun setNidUploaded(uploaded: Boolean) {
        _isNidUploaded.value = uploaded
    }

    fun setFaceMatchVerified(verified: Boolean) {
        _isFaceMatchVerified.value = verified
    }

    fun depositFunds(amount: Int) {
        viewModelScope.launch {
            val shortId = UUID.randomUUID().toString().substring(0, 5).uppercase()
            repository.insertTransaction(
                TransactionEntity(
                    type = "DEPOSIT",
                    tokenCode = "TXN-DEP-$shortId-LEDGER",
                    amountCoins = amount,
                    status = "LEDGER_VALID"
                )
            )
        }
    }

    fun initiateBookingSimulation() {
        if (_pickupLocation.value.isBlank() || _destinationLocation.value.isBlank()) return

        viewModelScope.launch {
            _isSearching.value = true
            _bookingStep.value = 2 // Move viewport to loading matching status

            // Animated search progress bar simulation
            for (i in 1..20) {
                delay(150)
                _searchProgress.value = i / 20f
            }

            // Companion assignment details based on selector filters
            val companionName = when (_preferredGender.value) {
                "Female" -> listOf("Tasnim Rahman", "Mousumi Sen", "Nadia Islam").random()
                "Male" -> listOf("Abir Hossain", "Sumon Sarker", "Sajid Sheikh").random()
                else -> listOf("Abir Hossain", "Tasnim Rahman", "Sajid Sheikh", "Mousumi Sen").random()
            }
            val vehicle = listOf(
                "Toyota Aqua Green Hybrid [Elite K-8291]",
                "Premium Secure Vespa [Metro S-1029]",
                "Tesla Cyber Assist [Zone G-5510]",
                "Elite Secure Cruiser [P-92040]"
            ).random()

            val chargeCoins = 1200 // Base premium companion fare

            // Create booking entity in Database
            val bookingId = repository.insertBooking(
                BookingEntity(
                    pickupLocation = _pickupLocation.value,
                    destinationLocation = _destinationLocation.value,
                    genderPreference = _preferredGender.value,
                    companionLanguage = _preferredLanguage.value,
                    pickupTime = _pickupTime.value,
                    status = "EN_ROUTE",
                    companionName = companionName,
                    companionVehicle = vehicle,
                    companionRating = 4.9f,
                    priceCoins = chargeCoins
                )
            ).toInt()

            // Insert matching ledger debit transaction
            val shortId = UUID.randomUUID().toString().substring(0, 5).uppercase()
            repository.insertTransaction(
                TransactionEntity(
                    type = "PAYMENT",
                    tokenCode = "TXN-MATCH-$shortId-DEBIT",
                    amountCoins = chargeCoins,
                    status = "LEDGER_VALID"
                )
            )

            // Dynamic UI updates in coroutine context
            _isSearching.value = false
            _activeTab.value = 1 // Routinely switch to the Pulse Radar tab
            
            // Wait 12 seconds in route, then switch to ongoing active travel status
            delay(12000)
            repository.updateBookingStatus(bookingId, "ACTIVE")
        }
    }

    fun completeActiveJourney(bookingId: Int) {
        viewModelScope.launch {
            repository.updateBookingStatus(bookingId, "COMPLETED")
            // Return viewport context to ready-booking step
            _bookingStep.value = 0
            _pickupLocation.value = ""
            _destinationLocation.value = ""
            _activeTab.value = 2 // Direct to Trust Wallet Ledger to see receipt logs
        }
    }

    fun cancelActiveJourney(bookingId: Int, priceCoins: Int) {
        viewModelScope.launch {
            repository.updateBookingStatus(bookingId, "CANCELLED")
            
            // Refund fare to wallet with safe reclaim tracking token
            val shortId = UUID.randomUUID().toString().substring(0, 5).uppercase()
            repository.insertTransaction(
                TransactionEntity(
                    type = "RECLAIM",
                    tokenCode = "TXN-REF-$shortId-RECLAIM",
                    amountCoins = priceCoins,
                    status = "LEDGER_VALID"
                )
            )

            _bookingStep.value = 0
            _pickupLocation.value = ""
            _destinationLocation.value = ""
            _activeTab.value = 0 // Return to setup engine
        }
    }

    private fun simulateCompanionMovement() {
        // Slow periodic coordinate stream offset simulation to update glowing pulse map smoothly
        viewModelScope.launch {
            var degree = 0.0
            while (true) {
                delay(1200)
                degree += 0.08
                // Simulate slow spiral orbital approaching path towards center point (user)
                val radialScale = maxOf(0.002f, 0.015f - (degree.toFloat() * 0.0002f))
                _companionLatitudeOffset.value = (Math.cos(degree) * radialScale).toFloat()
                _companionLongitudeOffset.value = (Math.sin(degree) * radialScale).toFloat()
            }
        }
    }
}
