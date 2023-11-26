package com.example.hotels_test.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotelInfoDto(
    @Json(name = "about_the_hotel")
    val aboutTheHotelDto: AboutTheHotelDto,
    @Json(name = "adress")
    val address: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_urls")
    val imageUrls: List<String>,
    @Json(name = "minimal_price")
    val minimalPrice: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "price_for_it")
    val priceForIt: String,
    @Json(name = "rating")
    val rating: Int,
    @Json(name = "rating_name")
    val ratingName: String,
)
