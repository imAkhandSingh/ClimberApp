package com.example.climberapp.config

import com.example.climberapp.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("appLogin")
    fun appLogin(
        @Field("email")email:String,
        @Field("password")password: String): Call<LoginResponse>
}