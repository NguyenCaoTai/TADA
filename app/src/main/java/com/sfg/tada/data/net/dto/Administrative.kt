package com.sfg.tada.data.net.dto

data class Administrative(
    val adminLevel: Int,
    val description: String,
    val geonameId: Int,
    val isoCode: String,
    val isoName: String,
    val name: String,
    val order: Int,
    val wikidataId: String
)