package com.example.climberapp.ui.model

data class Alarm(
    val alert: Int,
    val battery: Double,
    val categery: String,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    val deviceId: Long,
    val deviceTripId: String,
    val fleetId: Int,
    val height: Float,
    val id: Int,
    val lastTouchDate: String,
    val lastTouchDateTime: String,
    val lastTouchTime: String,
    val lat: Double,
    val lon: Double,
    val pktype: String,
    val releasedAt: String,
    val siteId: String,
    val status: Int,
    val ts: Int,
    val updatedAt: String,
    val updatedBy: String,
    val username: Any
)