package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jdm_app.activity.MainActivity.CustomerObject.customer
import com.example.jdm_app.databinding.CarDetailBinding
import com.example.jdm_app.databinding.CustomerDetailBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Customer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CustomerDetailActivity : AppCompatActivity() {

    private lateinit var binding: CustomerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindCustomerData(MainActivity.customer as Customer)
        setupBackButton()
        setupEditButton()
    }

    private fun bindCustomerData(customer: Customer) {

        var date = LocalDate.parse(customer.dateOfBirth)
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var formattedDate = date?.format(formatter)

        binding.textUsername.text = "Username: ${customer.username}"
        binding.textDateOfBirth.text = "Date of birth: ${formattedDate.toString()}"
        binding.textAddress.text = "Address: ${customer.address}"
        binding.textPhoneNumber.text = "Phone number: ${customer.phoneNumber}"
    }

    private fun setupBackButton() {
        binding.buttonBackProfile.setOnClickListener {
            finish()
        }
    }

    private fun setupEditButton() {
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(this, CustomerEditActivity::class.java)
            intent.putExtra("customer", customer)
            this.startActivity(intent)
        }
    }

}
