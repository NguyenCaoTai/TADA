package com.sfg.tada.data.net.service

import com.sfg.tada.data.net.dto.AqiResp
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AqiService {
    @GET("feed/geo:{lat};{lng}/")
    fun aqiInfo(
        @Path("lat") lat: Double,
        @Path("lng") lng: Double,
        @Query("token") token: String = "8f46bdc5b302fb3f79f8d2e297fcaab1cd63aa7f"
    ): Flow<AqiResp>
}