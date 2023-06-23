package com.example.climberapp.ui.sites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climberapp.R
import com.example.climberapp.ui.viewmodel.SitesViewModel

class SitesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: SitesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sites, container, false)
        recyclerView = view.findViewById(R.id.sitesRecyclerView)

        initRecyclerView()
        initViewModel()
        SitesViewModel()
        return view
    }
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = SitesAdapter()
        recyclerView.adapter = recyclerAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        val viewModel: SitesViewModel =
            ViewModelProvider(this)[SitesViewModel::class.java]
        viewModel.getSiteDataObserver()?.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerAdapter.setSiteList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.fetchSites()
    }
}