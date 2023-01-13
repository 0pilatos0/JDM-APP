package com.example.jdm_app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.jdm_app.view.CarViewModel
import com.example.jdm_app.adapter.OwnedCarAdapter
import com.example.jdm_app.data.LocalDatabase
import com.example.jdm_app.databinding.CarsOwnedBinding
import com.example.jdm_app.databinding.RegistrationPageBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Customer
import com.example.jdm_app.service.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: RegistrationPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this
        binding.registerButton.setOnClickListener {
            if (customerIsValid()) {
                CoroutineScope(Dispatchers.IO).launch {


                    //TODO ECHTE DATA ERIN!!
                    //id moet null blijven zodat de api deze kan asignen

                    val newCustomer = Customer(
                        null,
                        "John" ,                             //binding.registerUsernameField.text.toString(),
                        "1990-01-01",                       //binding.registerDateOfBirthField.text.toString(),
                        "1234",                                //binding.registerAddressField.text.toString(),
                        "1234"                    //binding.registerNumberField.text.toString()
                    )

                    val customerResponse = CustomerApi.retrofitService.createCustomer(newCustomer)

                    val customer : Customer? = customerResponse.body()

                    //check if customer is a Customer object
                    if (customer != null) {
                        val db = Room.databaseBuilder(
                            applicationContext,
                            LocalDatabase::class.java, "local_database"
                        ).build()
                        val customerDao = db.customerDao()
                        customerDao.insert(customer)

                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("customer", customer)
                        context.startActivity(intent)
                    }
                }
            }
        }

    }

    private fun customerIsValid(): Boolean {
        var isValid = true
        if (binding.registerUsernameField.text.toString().isEmpty()) {
            binding.registerUsernameField.error = "Username is required"
            isValid = false
        }
        if (binding.registerDateOfBirthField.text.toString().isEmpty()) {
            binding.registerDateOfBirthField.error = "Date of birth is required"
            isValid = false
        }
        if (binding.registerAddressField.text.toString().isEmpty()) {
            binding.registerAddressField.error = "Address is required"
            isValid = false
        }
        if (binding.registerNumberField.text.toString().isEmpty()) {
            binding.registerNumberField.error = "Phone number is required"
            isValid = false
        }
        return isValid
    }
}