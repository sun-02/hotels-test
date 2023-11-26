package com.example.hotels_test.ui.hotel

data class HotelState(
    val description: String,
    val peculiarities: List<String>,
    val address: String,
    val imageUrls: List<String>,
    val minimalPrice: Int,
    val name: String,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
)