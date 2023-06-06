package com.example.climberapp.ui.sites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SitesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is sites Fragment"
    }
    val text: LiveData<String> = _text
}