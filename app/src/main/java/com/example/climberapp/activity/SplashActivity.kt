package com.example.climberapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.climberapp.MainActivity
import com.example.climberapp.R
import com.example.climberapp.config.CheckInternet
import com.example.climberapp.storage.SharedPrefManager

class SplashActivity : AppCompatActivity() {

    lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        userId = SharedPrefManager.getInstance(this@SplashActivity).getString("userDeviceId","NA").toString()
        Log.d("usr",userId)
        Handler(Looper.getMainLooper()).postDelayed({
            if(CheckInternet.getInstance(this@SplashActivity).isNetworkConnected()){
                if(!userId.equals("NA")){
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }, 3000)

    }
}