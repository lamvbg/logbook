package com.example.logbook.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    // You can modify this to hold the result of the conversion
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    fun setResult(value: String) {
        _result.value = value
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is the dashboard Fragment"
    }
    val text: LiveData<String> = _text
}
