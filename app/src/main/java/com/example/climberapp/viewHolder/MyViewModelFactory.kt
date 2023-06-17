package com.example.climberapp.viewHolder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climberapp.repository.Repository
import com.example.climberapp.ui.devices.DeviceViewModel

class MyViewModelFactory constructor(private val repository: Repository, val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DeviceViewModel::class.java)) {
            DeviceViewModel(this.repository, context) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}