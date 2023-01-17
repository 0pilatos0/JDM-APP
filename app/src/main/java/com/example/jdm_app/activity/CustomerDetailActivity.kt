package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.jdm_app.R
import com.example.jdm_app.activity.MainActivity.CustomerObject.customer
import com.example.jdm_app.data.LocalDatabase
import com.example.jdm_app.databinding.CarDetailBinding
import com.example.jdm_app.databinding.CustomerDetailBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Customer
import com.example.jdm_app.service.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CustomerDetailActivity : AppCompatActivity() {

    private lateinit var binding: CustomerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindCustomerData(MainActivity.customer as Customer)
        setupEditButton()
        setupDeleteButton()

        binding.bottomNavigationView.selectedItemId = R.id.action_profile

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation -> {
                    val intent = Intent(this, ReservationActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_cars -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_profile -> {
                    val intent = Intent(this, CustomerDetailActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
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

    private fun setupEditButton() {
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(this, CustomerEditActivity::class.java)
            intent.putExtra("customer", customer)
            this.startActivity(intent)
        }
    }

    private fun setupDeleteButton() {
        binding.buttonDelete.setOnClickListener {

            val localDatabase = LocalDatabase.getDatabase(this)

            customer = MainActivity.customer as Customer

            CoroutineScope(Dispatchers.IO).launch {

                localDatabase.customerDao().delete(customer!!)

                val customerResponse = customer!!.id?.let { it1 ->
                    CustomerApi.retrofitService.deleteCustomer(
                        it1
                    )
                }
                val deletedCustomer: Response<Void>? = customerResponse
                if (deletedCustomer != null) {
                    if (deletedCustomer.isSuccessful) {
                        val intent =
                            Intent(this@CustomerDetailActivity, RegistrationActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

}
