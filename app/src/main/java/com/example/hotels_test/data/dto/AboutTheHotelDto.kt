package com.example.hotels_test.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AboutTheHotelDto(
    @Json(name = "description")
    val description: String,
    @Json(name = "peculiarities")
    val peculiarities: List<String>
)