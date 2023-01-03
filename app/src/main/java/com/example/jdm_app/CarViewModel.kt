package com.example.jdm_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {

    private val _todoResponse: MutableLiveData<String> = MutableLiveData()
    val todoResponse: LiveData<String> // this livedata value is bound to a TextView.text property
        get() = _todoResponse

    init {
        getCars()
    }

    fun getCars() {
        viewModelScope.launch {
            val cars = CarApi.retrofitService.getCars().body().toString()
            _todoResponse.value = cars
        }
    }


}