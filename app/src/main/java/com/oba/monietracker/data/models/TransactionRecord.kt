package com.oba.monietracker.data.models

import java.util.Date

/**
 * The transaction record model.
 */
data class TransactionRecord(
    val date: String,
    val amount: Float,
    val type: String,
    val description: String,
    val category: String,
    val transactionMethod: String = "credit",
    val createdAt: Date = Date()
)
