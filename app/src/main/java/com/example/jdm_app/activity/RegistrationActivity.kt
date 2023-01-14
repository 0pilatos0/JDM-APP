package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.jdm_app.data.LocalDatabase
import com.example.jdm_app.databinding.RegistrationPageBinding
import com.example.jdm_app.domain.Customer
import com.example.jdm_app.service.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


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

                    val day: Int = binding.registerDateOfBirthField.getDayOfMonth()
                    val month: Int = binding.registerDateOfBirthField.getMonth()
                    val year: Int = binding.registerDateOfBirthField.getYear()
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.set(year, month, day)

                    val date = SimpleDateFormat("yyyy-MM-dd")
                    val formatedDate: String = date.format(calendar.time)


                    val newCustomer = Customer(
                        null,
                        binding.registerUsernameField.text.toString(),
                        formatedDate,
                        binding.registerAddressField.text.toString(),
                        binding.registerNumberField.text.toString()
                    )
//
//                    "John" ,
//                    "1990-01-01",
//                    "1234",
//                    "1234"
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
//        if (binding.registerDateOfBirthField.isEmpty()) {
//            binding.registerDateOfBirthField.err = "Date of birth is required"
//            isValid = false
//        }
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