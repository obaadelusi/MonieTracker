package com.oba.monietracker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosData(
    @Json(name = "results")
    val results: List<Photo?>?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "total_pages")
    val totalPages: Int?
)