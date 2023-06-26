package com.example.climberapp.ui.model

data class Site(
    val active: Int,
    val antennaHeight: Int,
    val city: String,
    val createdAt: String,
    val createdBy: String,
    val id: Int,
    val lastTouchDate: String,
    val lastTouchDateTime: String,
    val lastTouchTime: String,
    val lat: Double,
    val lon: Double,
    val msl: Double,
    val name: String,
    val siteClassification: String,
    val siteId: String,
    val updatedAt: String,
    val updatedBy: String
)