package com.example.hotels_test.data

import com.example.hotels_test.data.dto.BookingInfoDto
import com.example.hotels_test.data.dto.HotelInfoDto
import com.example.hotels_test.data.dto.RoomsInfoDto
import retrofit2.http.GET

interface HotelsApi {

    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun fetchHotelInfo(): HotelInfoDto

    @GET("/v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun fetchRoomsInfo(): RoomsInfoDto

    @GET("/v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun fetchBookingInfo(): BookingInfoDto
}