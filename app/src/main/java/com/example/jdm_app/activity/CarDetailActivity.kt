package com.example.jdm_app.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jdm_app.adapter.ImageAdapter
import com.example.jdm_app.databinding.CarDetailBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.service.ReservationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarDetailActivity : AppCompatActivity() {

    private lateinit var binding: CarDetailBinding
    private lateinit var reservation: Reservation

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val car = intent.getSerializableExtra("car") as Car
        binding.textViewTitle.text = "${car.color} ${car.brand}"
        binding.textViewDescription.text = "${car.description}"
        binding.textViewCarType.text = "Car type: ${car.carType}"
        binding.textViewLicensePlate.text = "License plate: ${car.licensePlate}"
        binding.textViewPrice.text = "Price: ${car.price}"
        binding.textViewCostPerKilometer.text = "Cost per kilometer: ${car.costPerKilometer}"
        binding.textViewSeats.text = "Seats: ${car.seats}"

        binding.buttonRent.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = ReservationApi.retrofitService.createReservation(reservation)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CarDetailActivity, "Car rented!", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@CarDetailActivity,
                            "Error occurred while renting car!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewCarImages.layoutManager = layoutManager
        binding.recyclerViewCarImages.adapter = ImageAdapter(car.images as MutableList<String>)
    }
}