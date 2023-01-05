package com.example.jdm_app.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jdm_app.databinding.CarDetailBinding
import com.example.jdm_app.databinding.CarEditBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.service.CarApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarEditActivity : AppCompatActivity() {

    private lateinit var binding: CarEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val car = intent.getSerializableExtra("car") as Car

        val carBrandPosition = when (car.brand) {
            "Audi" -> 0
            "Bentley" -> 1
            "BMW" -> 2
            "Buick" -> 3
            "Cadillac" -> 4
            "Chevrolet" -> 5
            "Chrysler" -> 6
            "Fiat" -> 7
            "Ford" -> 8
            else -> 0
        }

        binding.editSpinnerCarBrand.setSelection(carBrandPosition)
        binding.editTextColor.setText(car.color)

        val carTypePosition = when (car.carType) {
            "ICE" -> 0
            "BEV" -> 1
            "FCEV" -> 2
            else -> 0
        }
        binding.editSpinnerCarType.setSelection(carTypePosition)


        binding.editTextLicensePlate.setText(car.licensePlate)
        binding.editTextPrice.setText(car.price.toString() ?: "0")
        binding.editTextCostPerKilometer.setText(car.costPerKilometer.toString() ?: "0")
        binding.editTextSeats.setText(car.seats.toString() ?: "0")
        binding.editTextDescription.setText(car.description)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonSave.setOnClickListener {
            car.brand = binding.editSpinnerCarBrand.selectedItem.toString()
            car.color = binding.editTextColor.text.toString()
            car.carType = binding.editSpinnerCarType.selectedItem.toString()
            car.licensePlate = binding.editTextLicensePlate.text.toString()
            car.price = binding.editTextPrice.text.toString().toInt()
            car.costPerKilometer = binding.editTextCostPerKilometer.text.toString().toDouble()
            car.seats = binding.editTextSeats.text.toString().toInt()
            car.description = binding.editTextDescription.text.toString()


            CoroutineScope(Dispatchers.IO).launch {

                if(car.id == null){
                    val response = CarApi.retrofitService.createCar(car)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@CarEditActivity, "Car updated!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            // Error occurred while updating car
                            Toast.makeText(this@CarEditActivity, "Error occurred while updating car!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    val response = CarApi.retrofitService.updateCar(car.id!!, car)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@CarEditActivity, "Car updated!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            // Error occurred while updating car
                            Toast.makeText(this@CarEditActivity, "Error occurred while updating car!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }



            }
        }

        binding.buttonDelete.setOnClickListener {

            if(car.id == null){
                finish()
            }

            CoroutineScope(Dispatchers.IO).launch {
                val response = CarApi.retrofitService.deleteCar(car.id!!)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CarEditActivity, "Car deleted!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@CarEditActivity, "Error occurred while deleting car!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}