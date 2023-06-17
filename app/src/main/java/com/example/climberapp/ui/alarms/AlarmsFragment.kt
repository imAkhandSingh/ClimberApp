package com.example.climberapp.ui.alarms

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climberapp.R
import com.example.climberapp.ui.viewmodel.AlarmFragmentViewModel
import java.util.*

class AlarmsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: AlarmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarms, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        initRecyclerView()
        initViewModel()
        AlarmFragmentViewModel()
        return view
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = AlarmAdapter()
        recyclerView.adapter = recyclerAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        val viewModel: AlarmFragmentViewModel =
            ViewModelProvider(this)[AlarmFragmentViewModel::class.java]
        viewModel.getLiveDataObserver()?.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerAdapter.setAlarmList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.fetchAlarms()
    }
}