package com.sfg.tada.data.net.service

import com.sfg.tada.data.net.dto.GeoResp
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("reverse-geocode-client?localityLanguage=en")
    fun getGeoInfo(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double
    ): Flow<GeoResp>
}