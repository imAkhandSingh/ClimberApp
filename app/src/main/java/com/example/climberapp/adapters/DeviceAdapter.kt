package com.example.climberapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.databinding.AdapterDevicelistBinding
import com.example.climberapp.models.DeviceListModel
import com.example.climberapp.ui.devices.DeviceViewModel
import com.example.climberapp.ui.devices.DevicesFragment
import java.util.Locale

class DeviceAdapter: RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>(),Filterable {
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
        return devices.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.binding.txtDeviceId.text = device.deviceId.toString()
        holder.binding.txtIssueto.text = device.issuedTo
        holder.binding.txtCity.text = device.city
        holder.binding.txtSites.text = device.siteId
        if (device.lastOnsite) {
            holder.binding.txtLocation.text = "Onsite"
        } else {
            holder.binding.txtLocation.text = "Offsite"
        }
    }

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
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
                        txtDevice.toString().contains(filterPattern)
                    ) {
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
            devices = results?.values as ArrayList<DeviceListModel>
            if (devices.size != 0) {
                notifyDataSetChanged()
            }
        }


    }

    class DeviceViewHolder(val binding: AdapterDevicelistBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}