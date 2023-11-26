package com.example.hotels_test.domain.hotel

data class HotelInfo(
    val aboutTheHotel: AboutTheHotel,
    val address: String,
    val id: Int,
    val imageUrls: List<String>,
    val minimalPrice: Int,
    val name: String,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
)
