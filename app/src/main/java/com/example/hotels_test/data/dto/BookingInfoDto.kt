package com.example.hotels_test.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookingInfoDto(
    @Json(name = "arrival_country")
    val arrivalCountry: String,
    @Json(name = "departure")
    val departure: String,
    @Json(name = "fuel_charge")
    val fuelCharge: Int,
    @Json(name = "horating")
    val hotelRating: Int,
    @Json(name = "hotel_adress")
    val hotelAddress: String,
    @Json(name = "hotel_name")
    val hotelName: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "number_of_nights")
    val numberOfNights: Int,
    @Json(name = "nutrition")
    val nutrition: String,
    @Json(name = "rating_name")
    val ratingName: String,
    @Json(name = "room")
    val room: String,
    @Json(name = "service_charge")
    val serviceCharge: Int,
    @Json(name = "tour_date_start")
    val tourDateStart: String,
    @Json(name = "tour_date_stop")
    val tourDateStop: String,
    @Json(name = "tour_price")
    val tourPrice: Int,
)