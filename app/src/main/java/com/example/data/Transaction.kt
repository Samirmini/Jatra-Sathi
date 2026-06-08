package com.example.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "wallet_transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,          // "DEPOSIT", "PAYMENT", "RECLAIM", "INSURANCE"
    val tokenCode: String,     // Encrypted transaction token like TXN-SHA-9281-90F
    val amountCoins: Int,
    val status: String,        // "LEDGER_VALID", "PENDING_VERIFICATION"
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface TransactionDao {
    @Query("SELECT * FROM wallet_transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM wallet_transactions")
    suspend fun clearAll()
}
