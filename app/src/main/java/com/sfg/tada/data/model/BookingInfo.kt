package com.sfg.tada.data.model

import com.sfg.tada.data.model.PlaceInfo

data class BookingInfo(
    val from: PlaceInfo,
    val to: PlaceInfo
)