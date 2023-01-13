package com.example.jdm_app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jdm_app.adapter.ImageAdapter
import com.example.jdm_app.databinding.CarDetailBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.domain.User

class CarDetailActivity : AppCompatActivity() {

    private lateinit var binding: CarDetailBinding

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
            var user = User()
            var reservation = Reservation()

            user.id = 1
            reservation.renter = user
            reservation.carListing = car

            val intent = Intent(this, ReservationEditActivity::class.java)
            intent.putExtra("reservation", reservation)
            this.startActivity(intent)
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