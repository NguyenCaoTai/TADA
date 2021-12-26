package com.sfg.tada.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.sfg.tada.data.model.BookingInfo
import com.sfg.tada.data.model.PlaceInfo
import com.sfg.tada.data.repo.InfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val infoRepo: InfoRepo
) : ViewModel() {
    private val _initLocation = PlaceInfo(-1.0, -1.0, "N/A", "N/A")
    val selectedLocation = MutableStateFlow<PlaceInfo>(_initLocation)

    private val _currentLocation = MutableSharedFlow<LatLng>(1)
    private val _locationFrom = MutableSharedFlow<LatLng>(1)
    private val _locationTo = MutableSharedFlow<LatLng>(1)
    private val _booking = MutableSharedFlow<Unit>(1)

    private val currentLocation: Flow<PlaceInfo> =
        _currentLocation
            .flatMapConcat { infoRepo.getPlaceInfo(it.latitude, it.longitude) }
            .onEach { selectedLocation.tryEmit(it) }

    val locationFrom: StateFlow<ViewState> =
        _locationFrom
            .flatMapConcat { infoRepo.getPlaceInfo(it.latitude, it.longitude) }
            .onEach { selectedLocation.tryEmit(it) }
            .map { ViewState.Fetched(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                ViewState.Init(_initLocation.copy(name = "Set A"))
            )

    val locationTo: StateFlow<ViewState> =
        _locationTo
            .flatMapConcat { infoRepo.getPlaceInfo(it.latitude, it.longitude) }
            .onEach { selectedLocation.tryEmit(it) }
            .map { ViewState.Fetched(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                ViewState.Init(_initLocation.copy(name = "Set B"))
            )

    val bookingInfo: SharedFlow<BookingInfo> =
        _booking
            .flatMapLatest { locationFrom.zip(locationTo) { from, to -> from to to } }
            .filter { it.first is ViewState.Fetched && it.second is ViewState.Fetched }
            .map { BookingInfo(it.first.info, it.second.info) }
            .shareIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    init {
        viewModelScope.launch { currentLocation.collect() }
    }

    fun init(latLng: LatLng?) {
        latLng?.let { _currentLocation.tryEmit(it) }
    }

    fun setBookingLocationFrom(latLng: LatLng?) {
        latLng?.let { _locationFrom.tryEmit(it) }
    }

    fun setBookingLocationTo(latLng: LatLng?) {
        latLng?.let { _locationTo.tryEmit(it) }
    }

    fun booking() {
        _booking.tryEmit(Unit)
    }
}

sealed class ViewState(val info: PlaceInfo) {
    data class Init(val empty: PlaceInfo) : ViewState(empty)
    data class Fetched(val fetched: PlaceInfo) : ViewState(fetched)
}