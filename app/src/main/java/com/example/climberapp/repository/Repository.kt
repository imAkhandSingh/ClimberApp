package com.example.climberapp.repository

import com.example.climberapp.retrofit.RetrofitService

class Repository constructor(private val retrofitService: RetrofitService) {

    fun getAllDevices() = retrofitService.getAllDevices()
}