package com.example.climberapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.MainActivity
import com.example.climberapp.R
import com.example.climberapp.adapters.AdapterDeviceDetail
import com.example.climberapp.config.RetrofitClient
import com.example.climberapp.models.LoginResponse
import com.example.climberapp.models.PojoDeviceDetail
import com.example.climberapp.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeviceDetailActivity : AppCompatActivity() {
    private val itemsList = ArrayList<PojoDeviceDetail>()
    private lateinit var devDetailAdapter: AdapterDeviceDetail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        val recyclerView: RecyclerView = findViewById(R.id.recycle_dev_detail)
        devDetailAdapter = AdapterDeviceDetail(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = devDetailAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hitUrlDetail(email:String, password: String) {
        val progressDialog : ProgressDialog
        progressDialog = ProgressDialog(this@DeviceDetailActivity)
        progressDialog.setMessage("Loading....")
        progressDialog.show()
        RetrofitClient.instance.appLogin(email, password)
            .enqueue(object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@DeviceDetailActivity, t.message, Toast.LENGTH_LONG).show()
                    Log.d("fail",t.message.toString())
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    //if(!response.body()?.status!!){ /*response.body()?.user!!*/
                    if(response.body()!=null) {
                        response.body()?.let {
                            if (response.body()?.status == 200) {
                                val LoginResponse = response.body() as LoginResponse
                             /*   SharedPrefManager.getInstance(this@LoginActivity).putString("userDeviceId",LoginResponse.userDeviceId.toString())
                                SharedPrefManager.getInstance(this@LoginActivity).putString("fleetId",LoginResponse.fleetId.toString())
                                SharedPrefManager.getInstance(this@LoginActivity).saveUser(LoginResponse.user)
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                hideKeyboard()*/
                            } else {
                                Toast.makeText(this@DeviceDetailActivity, response.body()?.error!!, Toast.LENGTH_LONG).show()
                               // hideKeyboard()
                            }
                        }
                    }else{
                        Toast.makeText(this@DeviceDetailActivity, "Something went wrong!!", Toast.LENGTH_LONG).show()
                        //hideKeyboard()
                    }
                    progressDialog.dismiss()
                }
            })

    }
}