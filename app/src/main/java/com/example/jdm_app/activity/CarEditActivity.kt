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
        binding.editTextMake.setText(car.brand)
        binding.editTextColor.setText(car.color)
        binding.editTextCarType.setText(car.carType)
        binding.editTextLicensePlate.setText(car.licensePlate)
        binding.editTextPrice.setText(car.price.toString())
        binding.editTextCostPerKilometer.setText(car.costPerKilometer.toString())
        binding.editTextSeats.setText(car.seats.toString())
        binding.editTextDescription.setText(car.description)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonSave.setOnClickListener {
            car.brand = binding.editTextMake.text.toString()
            car.color = binding.editTextColor.text.toString()
            car.carType = binding.editTextCarType.text.toString()
            car.licensePlate = binding.editTextLicensePlate.text.toString()
            car.price = binding.editTextPrice.text.toString().toInt()

        //TODO FIX THIS TO CAST CORRECT
//            car.costPerKilometer = binding.editTextCostPerKilometer.text.toString().toInt().toDouble()
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