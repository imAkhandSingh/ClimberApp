package com.example.climberapp.ui.config



import com.example.climberapp.ui.model.User
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("appAlarms")
    fun getAppAlarms(): Call<User>
}

//    @FormUrlEncoded
//@Field("category")category: String,
//        @Field("deviceId")deviceId: Long,
//        @Field("pktype")definition: String,
//        @Field("lastTouchDateTime")time: Time)