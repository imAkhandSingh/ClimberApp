package  com.example.climberapp.viewHolder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climberapp.models.DeviceListModel
import com.example.climberapp.repository.DeviceRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeviceViewModel constructor(private val repository: DeviceRepository)  : ViewModel() {

    val deviceList = MutableLiveData<List<DeviceListModel>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllDevices() {

        val response = repository.getAllDevices()
        response.enqueue(object : Callback<List<DeviceListModel>> {
            override fun onResponse(call: Call<List<DeviceListModel>>, response: Response<List<DeviceListModel>>) {
                deviceList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<DeviceListModel>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}