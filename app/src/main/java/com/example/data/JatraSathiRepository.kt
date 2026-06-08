package com.example.data

import kotlinx.coroutines.flow.Flow

class JatraSathiRepository(private val database: AppDatabase) {
    val bookingDao = database.bookingDao()
    val transactionDao = database.transactionDao()

    val allBookings: Flow<List<BookingEntity>> = bookingDao.getAllBookings()
    val activeBooking: Flow<BookingEntity?> = bookingDao.getActiveBooking()
    val allTransactions: Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()

    suspend fun insertBooking(booking: BookingEntity): Long {
        return bookingDao.insertBooking(booking)
    }

    suspend fun updateBookingStatus(id: Int, status: String) {
        bookingDao.updateStatus(id, status)
    }

    suspend fun insertTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun clearAll() {
        bookingDao.clearAll()
        transactionDao.clearAll()
    }
}
