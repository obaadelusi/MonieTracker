package com.oba.monietracker.data.models

data class TransactionRecord(
    val date: String,
    val amount: Float,
    val type: String,
    val description: String,
    val category: String
)
