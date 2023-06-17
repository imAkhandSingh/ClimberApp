package com.example.climberapp.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climberapp.ui.config.Api
import com.example.climberapp.ui.config.RetrofitClient
import com.example.climberapp.ui.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmFragmentViewModel : ViewModel() {
    var liveDataList: MutableLiveData<User?>? = MutableLiveData()

    @SuppressLint("StaticFieldLeak")

    fun getLiveDataObserver(): LiveData<User?>? {
        return liveDataList
    }
    private fun makeApiCall() {
        val retrofitClient = RetrofitClient.getRetrofitInstance()
        val apiService = retrofitClient.create(Api::class.java)
        val call = apiService.getAppAlarms()
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                // Handle API failure case
                liveDataList?.postValue(null)
                Log.e("API Failure", t.message.toString())
            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                Log.d("response..........", response.toString())
                liveDataList?.postValue(response.body())
            }
        })
    }

    fun fetchAlarms() {
        makeApiCall()
    }
}