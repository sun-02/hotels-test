package com.example.hotels_test.data

import com.example.hotels_test.data.dto.BookingInfoDto
import com.example.hotels_test.data.dto.HotelInfoDto
import com.example.hotels_test.data.dto.RoomsInfoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface HotelsRemoteSource {

    suspend fun fetchHotelInfo(): Flow<HotelInfoDto>

    suspend fun fetchRoomsInfo(): Flow<RoomsInfoDto>

    suspend fun fetchBookingInfo(): Flow<BookingInfoDto>
}

class HotelsRemoteSourceImpl(
    private val hotelsApi: HotelsApi
) : HotelsRemoteSource {

    override suspend fun fetchHotelInfo() = flow {
        emit(hotelsApi.fetchHotelInfo())
    }

    override suspend fun fetchRoomsInfo() = flow {
        emit(hotelsApi.fetchRoomsInfo())
    }

    override suspend fun fetchBookingInfo() = flow {
        emit(hotelsApi.fetchBookingInfo())
    }
}
