package com.example.climberapp.repository

import com.example.climberapp.retrofit.RetrofitService

class DeviceRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllDevices() = retrofitService.getAllDevices()
}