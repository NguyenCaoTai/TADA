package com.sfg.tada.data.repo

import com.sfg.tada.data.net.service.AqiService
import com.sfg.tada.data.model.PlaceInfo
import com.sfg.tada.data.net.service.PlaceService
import com.sfg.tada.data.model.GeoInfo
import com.sfg.tada.data.net.dto.LocalityInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class InfoRepoImpl(
    private val aqiService: AqiService,
    private val placeService: PlaceService
) : InfoRepo {
    override fun getAQI(lat: Double, lng: Double): Flow<Int> =
        aqiService.aqiInfo(lat, lng)
            .map { it.data }
            .map { it.aqi }

    override fun getLocationInfo(lat: Double, lng: Double): Flow<GeoInfo> =
        placeService.getGeoInfo(lat, lng)
            .map { GeoInfo(it.latitude, it.longitude, getGeoName(it.localityInfo)) }

    override fun getPlaceInfo(lat: Double, lng: Double): Flow<PlaceInfo> =
        getAQI(lat, lng)
            .zip(getLocationInfo(lat, lng)) { aqi, geo ->
                PlaceInfo(geo.lat, geo.lng, "$aqi", geo.name)
            }

    private fun getGeoName(localityInfo: LocalityInfo): String =
        localityInfo
            .administrative
            .sortedByDescending { it.order }
            .take(2)
            .joinToString(", ")
            { ad -> ad.name }
}