package com.example.hotels_test.ui.booking

data class TouristViewHolderState(
    val title: String,
    var showErrors: Boolean = false,
    var isOpened: Boolean = true,
    var touristInfo: TouristInfo = TouristInfo(),
)