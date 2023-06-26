package com.example.climberapp.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.config.CheckInternet
import com.example.climberapp.config.PaginationScrollListener
import com.example.climberapp.config.RetrofitClient
import com.example.climberapp.models.ClampDetail
import com.example.climberapp.models.PojoDeviceDetail
import com.utpl.dkctradexn.adapter.DevDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class DeviceDetailActivity : AppCompatActivity() {
    private var itemsList = ArrayList<ClampDetail>()
    private lateinit var pojoDeviceDetail:PojoDeviceDetail
    private lateinit var devDetailAdapter: DevDetailAdapter
    private lateinit var recyclerView:RecyclerView
    private lateinit var Img_back:ImageView
    private lateinit var txt_issue_to:TextView
    private lateinit var txt_site_id:TextView
    private lateinit var txt_status:TextView
    private lateinit var txt_city:TextView
    private lateinit var txt_location:TextView
    private lateinit var txt_battery:TextView
    private val mIsLoading = true
    private  var mIsLastPage:Boolean = false
    private  var isStopCall:Boolean = false
    var page:Int = 0
    var totalPage:Int = 0
    var totalResult:Int = 0
    private lateinit var layoutManager:LinearLayoutManager
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = getColor(R.color.purple_700)
        }
        recyclerView = findViewById(R.id.recycle_dev_detail)
        txt_issue_to = findViewById(R.id.txt_issuetto)
        txt_site_id = findViewById(R.id.txt_siteId)
        txt_status = findViewById(R.id.txt_status)
        txt_city = findViewById(R.id.txt_city)
        txt_location = findViewById(R.id.txt_location)
        txt_battery = findViewById(R.id.txt_battery)
        Img_back = findViewById(R.id.img_back)
        val ss: Long = intent.getLongExtra("deviceId",0)!!.toLong()
        devDetailAdapter = DevDetailAdapter(this@DeviceDetailActivity,itemsList)
        layoutManager = LinearLayoutManager(applicationContext)

        if (ss!=null){
            if (CheckInternet.getInstance(this@DeviceDetailActivity).isNetworkConnected()){
            hitUrlDetail(ss.toLong(),0)
            }else{
                Toast.makeText(this@DeviceDetailActivity, "Check Internet Connectivity", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this@DeviceDetailActivity, "null in deviceid", Toast.LENGTH_LONG).show()
        }

        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
             override fun loadMoreItems() {
//            if (CheckInternet.getInstance(this@DeviceDetailActivity)) {
                    if (pojoDeviceDetail != null && isStopCall) {

                        hitUrlDetail(ss.toLong(),pojoDeviceDetail.currentPage+1)
                        isStopCall = false
                        if (pojoDeviceDetail.currentPage >= totalPage) {
                           devDetailAdapter .testLoad(true)
                        }
                        Log.d("page",pojoDeviceDetail.currentPage.toString())
                    }
//                } else {
//                    Snackbar.make(recyclerView, "No Internet connection..!!", 5000).show()
//                }
            }

            val totalPageCount: Int
                get() = totalPage
            override val isLastPage: Boolean
                get() = mIsLastPage
            override val isLoading: Boolean
                get() = mIsLoading
        })


        Img_back.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hitUrlDetail(deviceId:Long, page: Int) {
        val progressDialog : ProgressDialog
        progressDialog = ProgressDialog(this@DeviceDetailActivity)
        progressDialog.setMessage("Loading....")
        progressDialog.show()
        RetrofitClient.instance.deviceDetail(deviceId, page)
            .enqueue(object: Callback<PojoDeviceDetail> {
                override fun onFailure(call: Call<PojoDeviceDetail>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@DeviceDetailActivity, t.message, Toast.LENGTH_LONG).show()
                    Log.d("fail",t.message.toString())
                }

                @SuppressLint("ResourceAsColor")
                override fun onResponse(call: Call<PojoDeviceDetail>, response: Response<PojoDeviceDetail>) {
                    Log.d("resp",response.toString())
                    //if(!response.body()?.status!!){ /*response.body()?.user!!*/
                    if(response.body()!=null) {
                        response.body()?.let {
                            if (response.body()?.status == 200) {
                                val pojoDeviceDetail = response.body() as PojoDeviceDetail
                                Log.d("response",response.body().toString())

                                txt_issue_to.setText(pojoDeviceDetail.deviceDetails.issuedTo)
                                txt_site_id.setText(pojoDeviceDetail.deviceDetails.siteId)

                                if (pojoDeviceDetail.deviceDetails.isClamp.toString().equals("0")){
                                    txt_status.setText("OFFLINE")
                                    txt_status.setTextColor(Color.RED)
                                }else{
                                    txt_status.setText("ONLINE")
                                    txt_status.setTextColor(Color.GREEN)
                                }

                                txt_city.setText(pojoDeviceDetail.deviceDetails.city)
                                if (pojoDeviceDetail.deviceDetails.lastLat!= 0.0 || pojoDeviceDetail.deviceDetails.lastLon != 0.0 ) {
                                    txt_location.setText("Onsite")
                                    //txt_location.setTextColor(Color.GREEN)
                                }else{
                                    txt_location.setText("Offsite")
                                   // txt_location.setTextColor(Color.RED)
                                }
                                txt_battery.setText(pojoDeviceDetail.deviceDetails.lastBattery.toString()+" %")
                           // getAddress(pojoDeviceDetail.deviceDetails.lastLat,pojoDeviceDetail.deviceDetails.lastLon)
                                if (pojoDeviceDetail.clampDetails!=null){
                                itemsList = pojoDeviceDetail?.clampDetails as ArrayList<ClampDetail>
                                    devDetailAdapter = DevDetailAdapter(this@DeviceDetailActivity,itemsList)
                                   //devDetailAdapter.add(itemsList)
                                    //layoutManager = LinearLayoutManager(applicationContext)
                                    recyclerView.layoutManager = layoutManager
                                    recyclerView.adapter = devDetailAdapter
                                }else{
                                    Toast.makeText(this@DeviceDetailActivity, "No Clamp Details", Toast.LENGTH_LONG).show()
                                }

                            } else {
                                Toast.makeText(this@DeviceDetailActivity, "response.body()?.error!!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }else{
                        Toast.makeText(this@DeviceDetailActivity, "Something went wrong!!", Toast.LENGTH_LONG).show()
                    }
                    progressDialog.dismiss()
                }
            })

    }

    fun getAddress(lat: Double,lan: Double){
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        val address: Address?
        var fulladdress = ""
        //28.656628,77.030922
        addresses = geocoder.getFromLocation(lat,lan, 1)

        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                address = addresses[0]
                fulladdress = address.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex
                var city = address.getLocality();
                var state = address.getAdminArea();
                var country = address.getCountryName();
                var postalCode = address.getPostalCode();
                var knownName = address.getFeatureName();
            Log.d("address",fulladdress)// Only if available else return NULL
            } else{
                fulladdress = "Location not found"
            }
        }
    }
}