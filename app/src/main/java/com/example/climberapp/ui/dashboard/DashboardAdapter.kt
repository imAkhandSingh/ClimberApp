package com.example.climberapp.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.ui.model.AlarmX
import com.example.climberapp.ui.model.Dashboard
import java.util.Locale

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.ViewHolder>(), Filterable {
    private var data: Dashboard? = null
    private var dataFiltered: Dashboard? = null

    fun setDashboardList(data: Dashboard?) {
        this.data = data
        this.dataFiltered = data
    }

    fun setDashboardListFiltered(data: Dashboard?) {
        this.dataFiltered = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cards_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.alarms?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        val itemCount= dataFiltered?.alarms?.size ?: 0
        return minOf(itemCount, 3)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.alarmCategory)
        val textView2: TextView = itemView.findViewById(R.id.alarmPkType)
        val textView3: TextView = itemView.findViewById(R.id.alarmDeviceId)
        val textView4: TextView = itemView.findViewById(R.id.alarmTime)

        fun bind(item: AlarmX) {
            textView1.text = item.categery
            textView2.text = item.pktype
            textView3.text = item.deviceId.toString()
            textView4.text = item.lastTouchDateTime

        }

    }

    override fun getFilter(): Filter {

        return dashboardFilter
    }

    private val dashboardFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<AlarmX> = ArrayList()
            if (constraint.isNullOrEmpty()) {
                // when searchview is empty
                filteredList.addAll(data?.alarms!! )
            } else {
                // when you type something // it also uses Locale in case capital letters different.
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()
                for (item in data?.alarms!!) {

                }
            }
            val results = FilterResults()
            results.values = filteredList

            return results
        }

        @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            dataFiltered = results?.values as Dashboard
            setDashboardListFiltered(dataFiltered)
        }

    }

}