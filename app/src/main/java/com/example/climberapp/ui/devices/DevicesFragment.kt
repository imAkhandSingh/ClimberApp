package com.example.climberapp.ui.devices

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.example.climberapp.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climberapp.adapters.DeviceAdapter
import com.example.climberapp.databinding.FragmentDevicesBinding
import com.example.climberapp.repository.Repository
import com.example.climberapp.retrofit.RetrofitService
import com.example.climberapp.viewHolder.MyViewModelFactory
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.whenCreated
import com.example.climberapp.models.DeviceListModel

class DevicesFragment : Fragment() {
    private val TAG = "MainActivity"


    lateinit var viewModel: DeviceViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = DeviceAdapter()

    private var _binding: FragmentDevicesBinding? = null
    //private lateinit var binding: ActivityMainBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       // val galleryViewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

    viewModel = ViewModelProvider(this, MyViewModelFactory(Repository(retrofitService),requireContext())).get(DeviceViewModel::class.java)
    binding.recyclerview.adapter = adapter

    viewModel.deviceList.observe(viewLifecycleOwner, Observer {
        Log.d(TAG, "onCreate: $it")
        activity?.let { it1 -> adapter.setDeviceList(it, it1) }
    })

    activity?.let {
        viewModel.errorMessage.observe(it, Observer {

    })
    }
    viewModel.getAllDevices()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView
        //searching(searchView as SearchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotBlank()||query.isNotEmpty()) {
                    adapter.filter.filter(query)
                   // adapter.notifyDataSetChanged()
                }
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotBlank()||newText.isNotEmpty()){
                    adapter.filter.filter(newText)
                   // adapter.notifyDataSetChanged()
                }
                return false
            }
        })

       /* searchView.setOnQueryTextFocusChangeListener { _ , hasFocus ->
            if (hasFocus) {
                Log.d("focused","sear")
            } else {
                    // searchView not expanded
                    viewModel.deviceList.observe(viewLifecycleOwner, Observer {
                        Log.d(TAG, "onCreate: $it")
                        adapter.setDeviceList(it)
                    })
                Log.d("colapsed","close")
            }
        }*/

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}