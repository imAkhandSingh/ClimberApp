package com.example.climberapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.climberapp.MainActivity
import com.example.climberapp.R
import com.example.climberapp.config.CheckInternet
import com.example.climberapp.config.RetrofitClient
import com.example.climberapp.models.LoginResponse
import com.example.climberapp.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var et_email: EditText
    lateinit var et_password : EditText
    lateinit var btn_submit  : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        et_email = findViewById(R.id.editTextemail)
        et_password = findViewById(R.id.editTextpass)
        btn_submit = findViewById(R.id.button_signin)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.button_signin->{
                val email = et_email.text.toString().trim()
                val password = et_password.text.toString().trim()

                if(email.isEmpty()){
                    et_email.error = "Email required"
                    et_email.requestFocus()
                }else if(password.isEmpty()){
                    et_password.error = "Password required"
                    et_password.requestFocus()
                }else{
                    if (CheckInternet.getInstance(this@LoginActivity).isNetworkConnected()){
                        loginCall(email, password)
                     }else{
                        Toast.makeText(this@LoginActivity, "Check Internet Connectivity", Toast.LENGTH_LONG).show()
                     }

                }
            }
       }
    }

    fun loginCall(email:String, password: String) {
        RetrofitClient.instance.appLogin(email, password)
            .enqueue(object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                    Log.d("fail",t.message.toString())
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    //if(!response.body()?.status!!){ /*response.body()?.user!!*/
                    if(response.body()!=null) {
                        response.body()?.let {
                            if (response.body()?.status == 200) {
                                val LoginResponse = response.body() as LoginResponse
                                SharedPrefManager.getInstance(this@LoginActivity).putString("userDeviceId",LoginResponse.userDeviceId.toString())
                                SharedPrefManager.getInstance(this@LoginActivity).putString("fleetId",LoginResponse.fleetId.toString())
                                SharedPrefManager.getInstance(this@LoginActivity).saveUser(LoginResponse.user)
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                hideKeyboard()
                            } else {
                                Toast.makeText(this@LoginActivity, response.body()?.error!!, Toast.LENGTH_LONG).show()
                                hideKeyboard()
                            }
                        }
                    }else{
                        Toast.makeText(this@LoginActivity, "Something went wrong!!", Toast.LENGTH_LONG).show()
                        hideKeyboard()
                    }
                }
            })

    }

    fun hideKeyboard() {
       // on below line getting current view.
       val view: View? = this.currentFocus

       // on below line checking if view is not null.
       if (view != null) {
           // on below line we are creating a variable // for input manager and initializing it.
           val inputMethodManager =
               getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

           // on below line hiding our keyboard.
           inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)

           // displaying toast message on below line.
           //Toast.makeText(this, "Key board hidden", Toast.LENGTH_SHORT).show()
       }
   }
}