package com.oba.monietracker.data.models

import java.util.Date

/**
 * The transaction category model.
 */
data class Category(
    val name: String,
    val description: String?,
    val image: String?,
    val dateCreated: Date = Date()
)
