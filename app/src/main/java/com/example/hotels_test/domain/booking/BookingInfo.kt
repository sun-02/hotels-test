package com.example.hotels_test.domain.booking

data class BookingInfo(
    val arrivalCountry: String,
    val departure: String,
    val fuelCharge: Int,
    val hotelRating: Int,
    val hotelAddress: String,
    val hotelName: String,
    val id: Int,
    val numberOfNights: Int,
    val nutrition: String,
    val ratingName: String,
    val room: String,
    val serviceCharge: Int,
    val tourDateStart: String,
    val tourDateStop: String,
    val tourPrice: Int,
)