package  com.example.climberapp.ui.devices

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climberapp.models.DeviceListModel
import com.example.climberapp.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeviceViewModel constructor(private val repository: Repository, private val context: Context)  : ViewModel() {

    val deviceList = MutableLiveData<List<DeviceListModel>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllDevices() {
        // Set up progress before call
        val progressDialog : ProgressDialog
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading....")
        progressDialog.show()

        val response = repository.getAllDevices()
        response.enqueue(object : Callback<List<DeviceListModel>> {
            override fun onResponse(call: Call<List<DeviceListModel>>, response: Response<List<DeviceListModel>>) {
                deviceList.postValue(response.body())
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<List<DeviceListModel>>, t: Throwable) {
                errorMessage.postValue(t.message)
                progressDialog.dismiss()
            }
        })
    }

}

