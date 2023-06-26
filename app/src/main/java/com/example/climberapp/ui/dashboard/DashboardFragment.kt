package com.example.climberapp.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.ui.alarms.AlarmsFragment

class DashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: DashboardAdapter

    private lateinit var textViewTotalDevices: TextView
    private lateinit var textViewOffsite: TextView
    private lateinit var textViewOnsite: TextView
    private lateinit var textViewOnline: TextView
    private lateinit var textViewOffline: TextView

  //  private lateinit var moreButton: AppCompatButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = view.findViewById(R.id.dashboardRecyclerView)

        textViewTotalDevices = view.findViewById(R.id.textTotalDevice)
        textViewOffsite = view.findViewById(R.id.textOffsite)
        textViewOnsite = view.findViewById(R.id.textOnsite)
        textViewOnline = view.findViewById(R.id.textOnline)
        textViewOffline = view.findViewById(R.id.textOffLine)

//        moreButton = view.findViewById(R.id.moreButton)
//
//        moreButton.setOnClickListener{
//            openAlarmFragment()
//        }


        DashboardViewModel()
        initViewModel()
        initRecyclerView()
        return view
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = DashboardAdapter()
        recyclerView.adapter = recyclerAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        val viewModel: DashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]
            viewModel.getLiveDataObserver()?.observe(viewLifecycleOwner) {
            if (it != null) {

                recyclerAdapter.setDashboardList(it)
                recyclerAdapter.notifyDataSetChanged()

                val totalDevices = it.totalDevices
                val totalOffsite = it.totalOffsite
                val totalOnsite = it.totalOnsite
                val totalOnline = it.totalOnline
                val totalOffline = it.totalOffline


                textViewTotalDevices.text = totalDevices.toString()
                textViewOffsite.text = totalOffsite.toString()
                textViewOnsite.text = totalOnsite.toString()
                textViewOnline.text = totalOnline.toString()
                textViewOffline.text = totalOffline.toString()

            } else {
                Toast.makeText(context, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.fetchTotalDeviceCall()
    }
//    private fun openAlarmFragment(){
//        val alarmFragment = AlarmsFragment() // Replace `AlarmFragment()` with your actual AlarmFragment instance creation code
//
//        val fragmentManager = requireActivity().supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.moreButton, alarmFragment) // Replace `R.id.fragmentContainer` with the ID of the container where you want to display the AlarmFragment
//        transaction.addToBackStack(null) // Optional: Add the transaction to the back stack
//        transaction.commit()
//    }
}