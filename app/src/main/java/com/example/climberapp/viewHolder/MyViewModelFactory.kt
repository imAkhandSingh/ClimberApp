package com.example.climberapp.viewHolder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climberapp.repository.DeviceRepository

class MyViewModelFactory constructor(private val repository: DeviceRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DeviceViewModel::class.java)) {
            DeviceViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}