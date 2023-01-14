package com.example.jdm_app.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import com.example.jdm_app.domain.Car
import com.example.jdm_app.service.CarApi
import com.example.jdm_app.service.ReservationApi
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

    fun getCarsByUserId(id: Int) {
        viewModelScope.launch {
            val cars = CarApi.retrofitService.getCarsByUserId(id).body()
            _carList.value = cars ?: emptyList()
        }
    }

    fun getReservationsByUserId(id: Int) {
        viewModelScope.launch {
            val reservations = ReservationApi.retrofitService.getReservationsByUserId(id).body()
            var cars = listOf<java.io.Serializable?>()
            if (reservations != null) {
                for (reservation in reservations) {
                    cars += reservation.carListing
                }
            }
            _carList.value = cars as List<Car>?
        }
    }
}