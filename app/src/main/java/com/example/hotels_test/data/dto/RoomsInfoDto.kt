package com.example.hotels_test.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomsInfoDto(
    @Json(name = "rooms")
    val roomDtos: List<RoomDto>
)