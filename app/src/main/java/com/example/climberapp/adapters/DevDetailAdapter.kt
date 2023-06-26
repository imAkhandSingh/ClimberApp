package com.utpl.dkctradexn.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.models.ClampDetail
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class DevDetailAdapter(context: Context, arrayList: MutableList<ClampDetail>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var arrayList: MutableList<ClampDetail>
    var context: Context
    var ll = false

    init {
        this.arrayList = arrayList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var mView: View? = null
        return if (viewType == 1) {
            mView = LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false)
            ProgressViewHolder(mView)
        } else {
            mView =
                LayoutInflater.from(context).inflate(R.layout.item_clamps, parent, false)
            ViewHolder(mView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val strs = arrayList.get(position).createdAt.split("T").toTypedArray()
            val convertedDate = convertStringToDate(strs[0])
            val convertedDateString = convertDateToString(convertedDate!!)
            val str2=strs[1].split("+")
            val str3  = str2[0].split(".")
            Log.d("str3",str3[0])
            holder.mTextName.setText(str3[0])
            holder.textMovment.setText("Moving")
            holder.dateText.setText(convertedDateString)
            holder.heightText.setText(meterInfeet(arrayList.get(position).antennaHeight).toString()+ "ft")
        }else if (holder is ProgressViewHolder) {
//           if (DeviceUtils.isNetworkAvailable(context)) {
             //   holder.mProgress.visibility = View.VISIBLE
//            } else {
//                val viewHolder = holder
//                viewHolder.mProgress.visibility = View.GONE
//                viewHolder.txtLoading.visibility = View.GONE
//            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (arrayList.size >= 10) {
            if (position == arrayList.size - 1 && !ll) {
                1
            } else {
                2
            }
        } else {
            2
        }
    }

    fun testLoad(setLoad: Boolean) {
        ll = setLoad
    }


    fun add(mc: ClampDetail) {
        arrayList.add(mc)
        notifyItemInserted(arrayList.size - 1)
    }

    fun addAll(mcList: List<ClampDetail?>) {
        for (mc in mcList) {
            if (mc != null) {
                add(mc)
            }
        }
    }

    fun getarrayList(): List<ClampDetail>? {
        return if (arrayList.size > 0) {
            arrayList
        } else null
    }


    private fun convertDateToString(d: Date): String? {
        val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy", Locale.getDefault())
        return simpleDateFormat.format(d)
    }

    fun convertStringToDate(datetimeString: String?): Date? {
        val mformat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
        var date: Date? = Date()
        try {
            date = mformat.parse(datetimeString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextName: TextView
        var textMovment: TextView
        var heightText: TextView
        var dateText: TextView
//        var progressBar_wish: ProgressBar
//        var progressBar_cart: ProgressBar

        init {
            mTextName = itemView.findViewById<TextView>(R.id.txt_time)
            textMovment = itemView.findViewById<TextView>(R.id.txt_movement)
            heightText = itemView.findViewById<TextView>(R.id.txt_height)
            dateText = itemView.findViewById<TextView>(R.id.txt_date)
        }
    }

    internal inner class ProgressViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var mProgress: ProgressBar
        var txtLoading: TextView

        init {
            mProgress = itemView.findViewById<ProgressBar>(R.id.progress_bar)
            txtLoading = itemView.findViewById<TextView>(R.id.txt_loading)
        }
    }

    companion object {
        var x = 0
    }

    fun meterInfeet(leng:Float):Float{
        var height:Float
        height = (leng*3.281).roundToInt().toFloat()
        return height
    }
}