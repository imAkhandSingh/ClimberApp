package com.example.climberapp.models

data class LoginResponse(val userDeviceId:Long, val fleetId: Int, val user: User, val status: Int, val error: String)