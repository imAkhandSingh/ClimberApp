package com.example.climberapp.ui.sites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.ui.model.Site
import com.example.climberapp.ui.model.SiteUser
import java.util.Locale


class SitesAdapter : RecyclerView.Adapter<SitesAdapter.ViewHolder>(), Filterable {

    private var data: SiteUser? = null
    private var dataFiltered: SiteUser? = null

    fun setSiteList(data: SiteUser?) {
        this.data = data
        this.dataFiltered = data
    }

    fun setSiteListFiltered(data: SiteUser?) {
        this.dataFiltered = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sites_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.sites?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return dataFiltered?.sites?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.site_name)
        val textView2: TextView = itemView.findViewById(R.id.site_id)
        val textView3: TextView = itemView.findViewById(R.id.antenna_height)
        val textView4: TextView = itemView.findViewById(R.id.status)
        val textView5: TextView = itemView.findViewById(R.id.city)

        fun bind(item: Site) {
            textView1.text = item.name
            textView2.text = item.siteId
            textView3.text = item.antennaHeight.toString()
            textView4.text = item.active.toString()
            textView5.text = item.city
        }
    }

    override fun getFilter(): Filter {

        return siteFilter
    }

    private val siteFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<Site> = ArrayList()
            if (constraint.isNullOrEmpty()) {
                // when searchview is empty
                filteredList.addAll(data?.sites!!)
            } else {
                // when you type something // it also uses Locale in case capital letters different.
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()
                for (item in data?.sites!!) {

                }
            }
            val results = FilterResults()
            results.values = filteredList

            return results
        }

        @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataFiltered = results?.values as SiteUser
            setSiteListFiltered(dataFiltered)
        }
    }
}


