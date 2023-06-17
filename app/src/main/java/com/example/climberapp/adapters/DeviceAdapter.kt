package com.example.climberapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.activity.DeviceDetailActivity
import com.example.climberapp.databinding.AdapterDevicelistBinding
import com.example.climberapp.models.DeviceListModel
import java.util.Locale

class DeviceAdapter: RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>(),Filterable {
    var devices = mutableListOf<DeviceListModel>()
    var devicesFiltered = mutableListOf<DeviceListModel>()
    lateinit var context :Context

    fun setDeviceList(devices: List<DeviceListModel>, context: Context) {
        this.devices = devices.toMutableList()
        this.devicesFiltered = devices.toMutableList()
        this.context= context
        notifyDataSetChanged()
    }
    fun setDeviceListFilter(devices: List<DeviceListModel>) {
        this.devicesFiltered = devices.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterDevicelistBinding.inflate(inflater, parent, false)
        return DeviceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return devicesFiltered.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devicesFiltered[position]
        holder.binding.txtDeviceId.text = device.deviceId.toString()
        holder.binding.txtIssueto.text = device.issuedTo
        holder.binding.txtCity.text = device.city
        holder.binding.txtSites.text = device.siteId
        holder.binding.linMore.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onClick(view: View?) {
                // Do some work here
             val intent = Intent(context, DeviceDetailActivity::class.java)
                context.startActivity(intent)
            }
         })
        if (device.lastOnsite) {
            holder.binding.txtLocation.text = "Onsite"
        } else {
            holder.binding.txtLocation.text = "Offsite"
        }
    }


    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<DeviceListModel> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                // when searchview is empty
                filteredList.addAll(devices)
            } else {
                // when you type something // it also uses Locale in case capital letters different.
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()
                // val filteredList: ArrayList<DeviceListModel> = ArrayList()
                for (item in devices) {
                    val txApp = item.issuedTo
                    val txCity = item.city
                    val txtDevice = item.deviceId
                    if (txApp.lowercase(Locale.getDefault()).contains(filterPattern) ||
                        txCity.lowercase(Locale.getDefault()).contains(filterPattern) ||
                        txtDevice.toString().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList

            return results
        }


        @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//          devices.clear()
//          devices.addAll(results!!.values as ArrayList<DeviceListModel>)
           // devices = results?.values as ArrayList<DeviceListModel>


            devicesFiltered = results?.values as ArrayList<DeviceListModel>
            setDeviceListFilter(devicesFiltered)
            /*if (devices.size != 0) {
                notifyDataSetChanged()
            }*/
        }


    }

    class DeviceViewHolder(val binding: AdapterDevicelistBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}
