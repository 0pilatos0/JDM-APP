package com.example.jdm_app.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jdm_app.domain.Car
import com.example.jdm_app.service.CarApi
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {

    private val _carList: MutableLiveData<List<Car>> = MutableLiveData()
    val carlist: LiveData<List<Car>> // this livedata value is bound to a TextView.text property
        get() = _carList

    init {
        getCars()
    }

    fun getCars() {
        viewModelScope.launch {
            val cars = CarApi.retrofitService.getCars().body()
            _carList.value = cars ?: emptyList()
        }
    }


}