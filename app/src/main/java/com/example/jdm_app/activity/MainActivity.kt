package com.example.jdm_app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.jdm_app.view.CarViewModel
import com.example.jdm_app.R
import com.example.jdm_app.adapter.CarAdapter
import com.example.jdm_app.adapter.CustomerAdapter
import com.example.jdm_app.data.LocalDatabase
import com.example.jdm_app.databinding.ActivityMainBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Customer
import com.example.jdm_app.service.CarApi
import com.example.jdm_app.view.CustomerViewModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigationView.selectedItemId = R.id.action_cars
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        isRegistered() // to check if there if the user is registered

        val carViewModel: CarViewModel by viewModels()
        carViewModel.carlist.observe(this) {
            val adapter = CarAdapter(this, it)
            binding.recyclerView.adapter = adapter
        }

        val customerViewModel: CustomerViewModel by viewModels()
        customerViewModel.customer.observe(this) {
            val adapter = it?.let { it1 -> CustomerAdapter(this, it1) }
            binding.recyclerView.adapter = adapter
        }

        binding.myCarsButton.setOnClickListener {
            val intent = Intent(this, OwnedCarsActivity::class.java)
            startActivity(intent)
        }

        binding.swipeRefresh.setOnRefreshListener {
            carViewModel.getCars()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation -> {
                    binding.recyclerView.visibility = RecyclerView.GONE
                    true
                }
                R.id.action_cars -> {
                    binding.recyclerView.visibility = RecyclerView.VISIBLE
                    true
                }
                R.id.action_profile -> {
                    binding.recyclerView.visibility = RecyclerView.GONE
                    val intent = Intent(this, CustomerDetailActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isRegistered()
    }

    /**
     * Checks if the user is registered in the database
     * This function is called on launch and when the user returns to the app
    */
    private fun isRegistered(){
        val db = Room.databaseBuilder(
            applicationContext,
            LocalDatabase::class.java, "local_database"
        ).build()

        val context: Context = this
        CoroutineScope(Dispatchers.IO).launch {
            val customerDao = db.customerDao()
            var customer: Customer = customerDao.getCustomer()

            if(customer == null && !intent.hasExtra("customer")){
                val intent = Intent(context, RegistrationActivity::class.java)
                startActivity(intent)
            }

            if(intent.hasExtra("customer")){
                customer = intent.getSerializableExtra("customer")
                        as Customer

                MainActivity.customer = customer
            } else
            {
                MainActivity.customer = customer
            }
        }
    }

    companion object CustomerObject {
        var customer: Customer? = null
    }

}