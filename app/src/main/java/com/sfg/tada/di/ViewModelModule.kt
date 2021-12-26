package com.sfg.tada.di

import com.sfg.tada.data.net.service.AqiService
import com.sfg.tada.data.net.service.PlaceService
import com.sfg.tada.data.repo.InfoRepo
import com.sfg.tada.data.repo.InfoRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providerInfoRepo(
        aqiService: AqiService,
        placeService: PlaceService
    ): InfoRepo =
        InfoRepoImpl(
            aqiService,
            placeService
        )
}