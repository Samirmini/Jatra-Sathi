package com.example.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pickupLocation: String,
    val destinationLocation: String,
    val genderPreference: String,
    val companionLanguage: String,
    val pickupTime: String,
    val status: String, // "ASSIGNING", "EN_ROUTE", "ACTIVE", "COMPLETED", "CANCELLED"
    val companionName: String?,
    val companionVehicle: String?,
    val companionRating: Float,
    val priceCoins: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY timestamp DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Query("SELECT * FROM bookings WHERE status != 'COMPLETED' AND status != 'CANCELLED' LIMIT 1")
    fun getActiveBooking(): Flow<BookingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("UPDATE bookings SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)

    @Query("DELETE FROM bookings")
    suspend fun clearAll()
}
