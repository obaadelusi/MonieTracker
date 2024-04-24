package com.oba.monietracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The transaction record model.
 */
@Entity(tableName = "tbl_transactionRecords")
data class TransactionRecord(
    val transactionDate: String,
    val amount: Double,
    val type: String,
    val description: String,
    var category: Category?,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
