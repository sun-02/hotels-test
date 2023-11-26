package com.example.hotels_test.data

import com.example.hotels_test.domain.booking.BookingInfo
import com.example.hotels_test.domain.hotel.AboutTheHotel
import com.example.hotels_test.domain.hotel.HotelInfo
import com.example.hotels_test.domain.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface HotelsRepository {

    suspend fun fetchHotelInfo(): Flow<HotelInfo>

    suspend fun fetchRoomsInfo(): Flow<List<Room>>

    suspend fun fetchBookingInfo(): Flow<BookingInfo>
}

class HotelsRepositoryImpl(
    private val remoteSource: HotelsRemoteSource
) : HotelsRepository {

    override suspend fun fetchHotelInfo() =
        remoteSource.fetchHotelInfo()
            .map {
                val aboutTheHotel = AboutTheHotel(
                    description = it.aboutTheHotelDto.description,
                    peculiarities = it.aboutTheHotelDto.peculiarities,
                )
                HotelInfo(
                    aboutTheHotel = aboutTheHotel,
                    address = it.address,
                    id = it.id,
                    imageUrls = it.imageUrls,
                    minimalPrice = it.minimalPrice,
                    name = it.name,
                    priceForIt = it.priceForIt,
                    rating = it.rating,
                    ratingName = it.ratingName,
                )
            }

    override suspend fun fetchRoomsInfo() =
        remoteSource.fetchRoomsInfo()
            .map {
                it.roomDtos.map { roomDto ->
                    Room(
                        id = roomDto.id,
                        imageUrls = roomDto.imageUrls,
                        name = roomDto.name,
                        peculiarities = roomDto.peculiarities,
                        price = roomDto.price,
                        pricePer = roomDto.pricePer,
                    )
                }
            }

    override suspend fun fetchBookingInfo() =
        remoteSource.fetchBookingInfo()
            .map {
                BookingInfo(
                    arrivalCountry = it.arrivalCountry,
                    departure = it.departure,
                    fuelCharge = it.fuelCharge,
                    hotelRating = it.hotelRating,
                    hotelAddress = it.hotelAddress,
                    hotelName = it.hotelName,
                    id = it.id,
                    numberOfNights = it.numberOfNights,
                    nutrition = it.nutrition,
                    ratingName = it.ratingName,
                    room = it.room,
                    serviceCharge = it.serviceCharge,
                    tourDateStart = it.tourDateStart,
                    tourDateStop = it.tourDateStop,
                    tourPrice = it.tourPrice,
                )
            }
}