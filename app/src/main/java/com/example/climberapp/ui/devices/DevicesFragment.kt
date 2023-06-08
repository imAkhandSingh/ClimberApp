package com.example.climberapp.ui.devices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climberapp.adapters.DeviceAdapter
import com.example.climberapp.databinding.FragmentDevicesBinding
import com.example.climberapp.repository.DeviceRepository
import com.example.climberapp.retrofit.RetrofitService
import com.example.climberapp.viewHolder.DeviceViewModel
import com.example.climberapp.viewHolder.MyViewModelFactory

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
/*
*  setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter

        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setMovieList(it)
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllMovies()*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       // val galleryViewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)


        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

    viewModel = ViewModelProvider(this, MyViewModelFactory(DeviceRepository(retrofitService))).get(DeviceViewModel::class.java)
    binding.recyclerview.adapter = adapter

    viewModel.deviceList.observe(viewLifecycleOwner, Observer {
        Log.d(TAG, "onCreate: $it")
        adapter.setDeviceList(it)
    })

    activity?.let {
        viewModel.errorMessage.observe(it, Observer {

    })
    }
    viewModel.getAllDevices()


//        val textView: TextView = binding.textDevices
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}