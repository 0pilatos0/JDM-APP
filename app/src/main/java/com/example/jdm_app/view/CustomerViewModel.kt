package com.example.jdm_app.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jdm_app.activity.MainActivity
import com.example.jdm_app.domain.Customer
import com.example.jdm_app.service.CarApi
import com.example.jdm_app.service.CustomerApi
import kotlinx.coroutines.launch

class CustomerViewModel : ViewModel() {

    private val _customer: MutableLiveData<Customer?> = MutableLiveData()
    val customer: MutableLiveData<Customer?> // this livedata value is bound to a TextView.text property
        get() = _customer

    init {
        getCustomer()
    }

    private fun getCustomer() {
        viewModelScope.launch {
            val customer =
                MainActivity.customer?.id?.let { CustomerApi.retrofitService.getCustomer(1).body() }
            _customer.value = customer
        }
    }

}