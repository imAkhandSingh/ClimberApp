package com.example.climberapp.ui.alarms

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.ui.model.Alarm
import com.example.climberapp.ui.model.User
import java.util.Locale


class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() ,Filterable{

    private var data: User? = null
    private var dataFiltered: User? = null

    fun setAlarmList(data: User?) {
        this.data = data
        this.dataFiltered =data
    }

    fun setAlarmListFilterd(data: User?) {
        this.dataFiltered =data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.alarms?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
            return dataFiltered?.alarms?.size?:0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // val textView1: TextView = itemView.findViewById(R.id.column1TextView)
        val textView2: TextView = itemView.findViewById(R.id.column2TextView)
      //  val textView3: TextView = itemView.findViewById(R.id.column3TextView)
        val textView4: TextView = itemView.findViewById(R.id.column4TextView)
       // val textView5: TextView = itemView.findViewById(R.id.column5TextView)
       // val editTextDeviceId: TextView = itemView.findViewById(R.id.deviceIdEditText)
        fun bind(item: Alarm) {
         //   textView1.text = item.categery
            textView2.text = item.deviceId.toString()
         //   textView3.text = item.pktype
            textView4.text = item.lastTouchDateTime
           // textView5.text = item.releasedAt

        }
    }

//    adapter.filter.filter(newText)
//    adapter.notifyDataSetChanged()

    override fun getFilter(): Filter {

        return exampleFilter
    }

    private val exampleFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<Alarm> = ArrayList()
            if (constraint.isNullOrEmpty()) {
                // when searchview is empty
                filteredList.addAll(data?.alarms!! )
            } else {
                // when you type something // it also uses Locale in case capital letters different.
               val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()
                for (item in data?.alarms!!) {
//                   val txApp = item.pktype
//                    val txCity = item.lastTouchDateTime
//                    val txtDevice = item.deviceId
//                    if (txApp.lowercase(Locale.getDefault()).contains(filterPattern) ||
//                        txCity.lowercase(Locale.getDefault()).contains(filterPattern) ||
//                        txtDevice.toString().contains(filterPattern)) {
//                        filteredList.add(item)
//                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList

            return results
        }

        @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            dataFiltered = results?.values as User
            setAlarmListFilterd(dataFiltered)
        }

    }

}