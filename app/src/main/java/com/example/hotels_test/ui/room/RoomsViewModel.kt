package com.example.hotels_test.ui.room

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okio.IOException

class RoomsViewModel(
    private val repository: HotelsRepository
) : ViewModel() {
    private val _state: MutableStateFlow<RoomsState?> = MutableStateFlow(null)
    val state: StateFlow<RoomsState?> = _state.asStateFlow()

    private val errorMsgChannel = Channel<ErrorMessage>()
    val errorMsg get() = errorMsgChannel.receiveAsFlow()

    init {
        getRoomsInfo()
    }

    fun getRoomsInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchRoomsInfo().map { infos ->
                RoomsState(infos)
            }.onEach  { state ->
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
                    RoomsViewModel(repository)
                }
            }
    }
}