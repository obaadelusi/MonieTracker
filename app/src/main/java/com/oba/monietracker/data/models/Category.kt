package com.oba.monietracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The transaction category model.
 */
@Entity(tableName = "tbl_categories")
data class Category(
    var name: String? = null,
    var description: String? = null,
    var imageId: String? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var createdAt: Long? = System.currentTimeMillis(),
)
