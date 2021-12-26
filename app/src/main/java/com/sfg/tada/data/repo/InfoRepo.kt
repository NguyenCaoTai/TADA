package com.sfg.tada.data.repo

import com.sfg.tada.data.model.PlaceInfo
import com.sfg.tada.data.model.GeoInfo
import kotlinx.coroutines.flow.Flow

interface InfoRepo {
    fun getAQI(lat: Double, lng: Double): Flow<Int>
    fun getLocationInfo(lat: Double, lng: Double): Flow<GeoInfo>
    fun getPlaceInfo(lat: Double, lng: Double): Flow<PlaceInfo>
}
