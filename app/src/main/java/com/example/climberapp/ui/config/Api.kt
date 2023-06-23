package com.example.climberapp.ui.config



import com.example.climberapp.ui.model.Dashboard
import com.example.climberapp.ui.model.SiteUser
import com.example.climberapp.ui.model.User
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("appAlarms")
    fun getAppAlarms(): Call<User>

    @GET("appSites")
    fun getAppSites(): Call<SiteUser>


    @GET("appDashboard")
    fun getTotalDevices(): Call<Dashboard>
}

//    @FormUrlEncoded
//@Field("category")category: String,
//        @Field("deviceId")deviceId: Long,
//        @Field("pktype")definition: String,
//        @Field("lastTouchDateTime")time: Time)