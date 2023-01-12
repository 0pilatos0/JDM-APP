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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: RegistrationPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerButton.setOnClickListener {
//            var user = Customer()
//            var car = Car()
//
//            user.id = 1
//            car.owner = user
            if (customerIsValid()) {
                val db = Room.databaseBuilder(
                    applicationContext,
                    LocalDatabase::class.java, "local_database"
                ).build()

                val context = this
                CoroutineScope(Dispatchers.IO).launch {
                    val customerDao = db.customerDao()
                    customerDao.insert(
                            Customer(
                            0,
                            binding.registerUsernameField.text.toString(),
                            binding.registerDateOfBirthField.text.toString(),
                            binding.registerAddressField.text.toString(),
                            binding.registerNumberField.text.toString()
                        )
                    )

                    val customer = Customer(
                        0,
                        binding.registerUsernameField.text.toString(),
                        binding.registerDateOfBirthField.text.toString(),
                        binding.registerAddressField.text.toString(),
                        binding.registerNumberField.text.toString()
                    )

                    val intent = Intent(context, RegistrationActivity::class.java)
                    intent.putExtra("customer", customer)
                    context.startActivity(intent)
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