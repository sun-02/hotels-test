package com.example.hotels_test.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.hotels_test.data.HotelsRepository
import com.example.hotels_test.ui.core.ErrorMessage
import com.example.hotels_test.ui.hotel.HotelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okio.IOException

class BookingViewModel(
    private val repository: HotelsRepository
) : ViewModel() {
    private val _state: MutableStateFlow<BookingState?> = MutableStateFlow(null)
    val state: StateFlow<BookingState?> = _state.asStateFlow()

    private val errorMsgChannel = Channel<ErrorMessage>()
    val errorMsg get() = errorMsgChannel.receiveAsFlow()

    init {
        getBookingInfo()
    }

    fun getBookingInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchBookingInfo().map { info ->
                BookingState(
                    arrivalCountry = info.arrivalCountry,
                    departure = info.departure,
                    fuelCharge = info.fuelCharge,
                    hotelRating = info.hotelRating,
                    hotelAddress = info.hotelAddress,
                    hotelName = info.hotelName,
                    numberOfNights = info.numberOfNights,
                    nutrition = info.nutrition,
                    ratingName = info.ratingName,
                    room = info.room,
                    serviceCharge = info.serviceCharge,
                    tourDateStart = info.tourDateStart,
                    tourDateStop = info.tourDateStop,
                    tourPrice = info.tourPrice,
                )
            }.onEach { state ->
                _state.emit(state)
            }.catch { e ->
                if (e is IOException) {
                    e.message?.let {
                        errorMsgChannel.send(ErrorMessage(it))
                    }
                } else {
                    throw e
                }
            }.collect()
        }
    }


    companion object {
        fun getFactory(repository: HotelsRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    BookingViewModel(repository)
                }
            }
    }
}