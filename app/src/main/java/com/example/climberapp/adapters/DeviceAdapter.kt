package com.example.climberapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.databinding.AdapterDevicelistBinding
import com.example.climberapp.models.DeviceListModel

class DeviceAdapter: RecyclerView.Adapter<DeviceViewHolder>() {
    var devices = mutableListOf<DeviceListModel>()

    fun setDeviceList(devices: List<DeviceListModel>) {
        this.devices = devices.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterDevicelistBinding.inflate(inflater, parent, false)
        return DeviceViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return  devices.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.binding.txtDeviceId.text = device.deviceId.toString()
        holder.binding.txtIssueto.text = device.issuedTo
        holder.binding.txtCity.text = device.city
        holder.binding.txtSites.text = device.siteId
        if (device.lastOnsite) {
            holder.binding.txtLocation.text = "Onsite"
        }else{
            holder.binding.txtLocation.text = "Offsite"
        }



    }


}

class DeviceViewHolder(val binding: AdapterDevicelistBinding) : RecyclerView.ViewHolder(binding.root) {}