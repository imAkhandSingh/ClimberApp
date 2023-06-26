package com.example.climberapp.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climberapp.ui.config.Api
import com.example.climberapp.ui.config.RetrofitClient
import com.example.climberapp.ui.model.SiteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SitesViewModel : ViewModel() {

    var siteDataList: MutableLiveData<SiteUser?>? = MutableLiveData()

    @SuppressLint("StaticFieldLeak")

    fun getSiteDataObserver(): LiveData<SiteUser?>? {
        return siteDataList
    }
    private fun makeApiCall() {
        val retrofitClient = RetrofitClient.getRetrofitInstance()
        val apiService = retrofitClient.create(Api::class.java)
        val call = apiService.getAppSites()
        call.enqueue(object : Callback<SiteUser> {
            override fun onFailure(call: Call<SiteUser>, t: Throwable) {
                // Handle API failure case
                siteDataList?.postValue(null)
                Log.e("API Failure", t.message.toString())
            }

            override fun onResponse(
                call: Call<SiteUser>,
                response: Response<SiteUser>
            ) {
                Log.d("response..........", response.toString())
                siteDataList?.postValue(response.body())
            }
        })
    }

    fun fetchSites() {
        makeApiCall()
    }
}



