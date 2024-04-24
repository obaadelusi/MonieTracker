package com.oba.monietracker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oba.monietracker.data.models.TransactionRecord

@Dao
interface TransactionRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTransactionRecords(transactions: List<TransactionRecord>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneTransactionRecord (transaction: TransactionRecord)

//    @Query("SELECT * FROM tbl_transactionRecords WHERE id = :id LIMIT 1")
//    fun getTransactionRecordById(id: Int): TransactionRecord

    @Query("SELECT * FROM tbl_transactionRecords ORDER BY createdAt DESC")
    fun getAllTransactionRecords(): MutableList<TransactionRecord>

    @Query("SELECT * FROM tbl_transactionRecords WHERE transactionDate LIKE :date ORDER BY createdAt DESC")
    fun getTransactionsByDate(date: String): MutableList<TransactionRecord>

    @Delete
    fun deleteTransactionRecord(transaction: TransactionRecord)

//    @Query("DELETE FROM tbl_transactionRecords WHERE id = :id")
//    fun deleteTransactionRecordById(id: String)

    @Query("DELETE FROM tbl_transactionRecords")
    fun deleteAllTransactions()
}