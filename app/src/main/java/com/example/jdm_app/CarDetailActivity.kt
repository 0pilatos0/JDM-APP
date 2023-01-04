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
        binding.textViewMake.text = "Make: ${car.brand}"
        binding.textViewModel.text = "Model: ${car.carType}"
        binding.textViewYear.text = "Year: ${car.price}"
        binding.textViewColor.text = "Color: ${car.color}"

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}