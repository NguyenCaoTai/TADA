package com.sfg.tada.di

import com.sfg.tada.data.net.service.AqiService
import com.sfg.tada.data.net.service.PlaceService
import com.sfg.tada.lib.FlowCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun providerAqiService(): AqiService =
        getRetrofitBuilder("https://api.waqi.info/")
            .create(AqiService::class.java)

    @Provides
    fun providerPlaceService(): PlaceService =
        getRetrofitBuilder("https://api.bigdatacloud.net/data/")
            .create(PlaceService::class.java)

    private fun getRetrofitBuilder(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()

    private fun getClient() =
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
            .let {
                OkHttpClient.Builder()
                    .addInterceptor(it)
                    .build()
            }
}