package com.example.jdm_app.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.service.CarApi
import com.example.jdm_app.service.ReservationApi
import kotlinx.coroutines.launch

class ReservationViewModel : ViewModel() {

    private val _reservationList: MutableLiveData<List<Reservation>> = MutableLiveData()
    val reservationlist: LiveData<List<Reservation>> // this livedata value is bound to a TextView.text property
        get() = _reservationList

    init {
        // Get current logged in user id
        getReservationsByUserId(1)
    }

    fun getReservationsByUserId(id: Int) {
        viewModelScope.launch {
            val reservations = ReservationApi.retrofitService.getReservationsByUserId(id).body()
            _reservationList.value = reservations ?: emptyList()
        }
    }
}