package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.jdm_app.data.LocalDatabase
import com.example.jdm_app.databinding.CustomerEditBinding
import com.example.jdm_app.domain.Customer
import com.example.jdm_app.service.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CustomerEditActivity : AppCompatActivity() {
    private lateinit var binding: CustomerEditBinding
    private lateinit var customer: Customer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomerEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customer = MainActivity.customer as Customer

        bindCustomerData()
        setupBackButton()
        setupSaveButton(customer)
    }

    private fun setupSaveButton(customer: Customer) {
        binding.buttonSave.setOnClickListener {

            val day: Int = binding.textDateOfBirth.getDayOfMonth()
            val month: Int = binding.textDateOfBirth.getMonth()
            val year: Int = binding.textDateOfBirth.getYear()
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val date = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate: String = date.format(calendar.time)

            customer.username = binding.textUsername.text.toString()
            customer.address = binding.textAddress.text.toString()
            customer.phoneNumber = binding.textPhoneNumber.text.toString()
            customer.dateOfBirth = formattedDate

            //update customer in local database
            val db = Room.databaseBuilder(
                applicationContext, LocalDatabase::class.java, "local_database"
            ).build()

            CoroutineScope(Dispatchers.IO).launch {

                db.customerDao().update(customer)

                val customerResponse = customer.id?.let { it1 ->
                    CustomerApi.retrofitService.updateCustomer(
                        it1, customer
                    )
                }
                val updatedCustomer: Customer? = customerResponse?.body()
                if (updatedCustomer != null) {
                    MainActivity.customer = updatedCustomer
                    val intent =
                        Intent(this@CustomerEditActivity, CustomerDetailActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setupBackButton() {
        binding.buttonBackProfile.setOnClickListener {
            finish()
        }
    }

    private fun bindCustomerData() {
        binding.textUsername.setText(customer.username)
        customer.dateOfBirth?.substring(0, 4)?.let {
            binding.textDateOfBirth.init(
                it.toInt(),
                customer.dateOfBirth!!.substring(5, 7).toInt(),
                customer.dateOfBirth!!.substring(8, 10).toInt(),
                null
            )
        }
        binding.textPhoneNumber.setText(customer.phoneNumber)
        binding.textAddress.setText(customer.address)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
    }

}
