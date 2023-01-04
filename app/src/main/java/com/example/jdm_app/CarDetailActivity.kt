package com.example.jdm_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jdm_app.databinding.CarDetailBinding

class CarDetailActivity : AppCompatActivity() {

    private lateinit var binding: CarDetailBinding

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

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}