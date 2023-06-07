package com.example.climberapp.config

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class CheckInternet private constructor(private val mCtx: Context) {

    @SuppressLint("MissingPermission")
     fun isNetworkConnected(): Boolean {
        val cm = mCtx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    companion object {
        private var mInstance: CheckInternet? = null
        @Synchronized
        fun getInstance(mCtx: Context): CheckInternet {
            if (mInstance == null) {
                mInstance = CheckInternet(mCtx)
            }
            return mInstance as CheckInternet
        }
    }
}