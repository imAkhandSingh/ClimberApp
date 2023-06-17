package com.example.climberapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.adapters.AdapterDeviceDetail.MyViewHolder
import com.example.climberapp.models.PojoDeviceDetail

class AdapterDeviceDetail(private var itemsList: ArrayList<PojoDeviceDetail>) : RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clamps, parent, false)
        return MyViewHolder(itemView)
       }

       override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
           val item = itemsList[position]
         //  holder.itemTextView.text = item
       }

       override fun getItemCount(): Int {
           return itemsList.size
       }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var itemTextView: TextView = view.findViewById(R.id.itemTextView)
    }
}