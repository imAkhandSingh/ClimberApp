package com.example.climberapp.ui.dashboard

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climberapp.ui.config.Api
import com.example.climberapp.ui.config.RetrofitClient
import com.example.climberapp.ui.model.Dashboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    var liveDataList: MutableLiveData<Dashboard?>? = MutableLiveData()

    @SuppressLint("StaticFieldLeak")

    fun getLiveDataObserver(): LiveData<Dashboard?>? {
        return liveDataList
    }
    private fun totalDeviceApiCall() {
        val retrofitClient = RetrofitClient.getRetrofitInstance()
        val apiService = retrofitClient.create(Api::class.java)
        val call = apiService.getTotalDevices()
        call.enqueue(object : Callback<Dashboard> {
            override fun onFailure(call: Call<Dashboard>, t: Throwable) {
                // Handle API failure case
                liveDataList?.postValue(null)
                Log.e("API Failure", t.message.toString())
            }

            override fun onResponse(
                call: Call<Dashboard>,
                response: Response<Dashboard>
            ) {
                Log.d("response..........", response.toString())
                liveDataList?.postValue(response.body())
            }
        })
    }

    fun fetchTotalDeviceCall() {
        totalDeviceApiCall()
    }
}