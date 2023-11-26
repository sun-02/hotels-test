package com.example.hotels_test.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_urls")
    val imageUrls: List<String>,
    @Json(name = "name")
    val name: String,
    @Json(name = "peculiarities")
    val peculiarities: List<String>,
    @Json(name = "price")
    val price: Int,
    @Json(name = "price_per")
    val pricePer: String
)