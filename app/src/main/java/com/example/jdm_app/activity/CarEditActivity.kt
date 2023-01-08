package com.example.jdm_app.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.R
import com.example.jdm_app.adapter.ImageAdapter
import com.example.jdm_app.databinding.CarEditBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.service.CarApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class CarEditActivity : AppCompatActivity() {

    private lateinit var binding: CarEditBinding
    private val SELECT_IMAGE_REQUEST_CODE = 0
    private lateinit var car: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        car = intent.getSerializableExtra("car") as Car

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
        binding.editTextPrice.setText(if(car.price.toString() == "0") "" else car.price.toString())
        binding.editTextCostPerKilometer.setText(if(car.costPerKilometer.toString() == "0.0") "" else car.costPerKilometer.toString())
        binding.editTextSeats.setText(if(car.seats.toString() == "0") "" else car.seats.toString())
        binding.editTextDescription.setText(car.description)

        binding.bottomCarEditNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
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
                    true
                }

                R.id.action_cancel -> {
                    finish()
                    true
                }

                R.id.action_save ->{
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
                                    Toast.makeText(this@CarEditActivity, "Error occurred while updating car!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    true
                }

                else -> false
            }
        }

        binding.buttonSelectImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewCarImages.layoutManager = layoutManager
        binding.recyclerViewCarImages.adapter = ImageAdapter(car.images as MutableList<String>)

        binding.buttonClearImages.setOnClickListener {
            car.images = mutableListOf()
binding.recyclerViewCarImages.adapter = ImageAdapter(car.images as MutableList<String>)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val outputStream = ByteArrayOutputStream()
            val scaledImageBitmap = Bitmap.createScaledBitmap(imageBitmap, 128, 128, false)
            scaledImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val scaledImageBytes = outputStream.toByteArray()

            val imageBase64 = Base64.encodeToString(scaledImageBytes, Base64.NO_WRAP)
            car.images?.add(imageBase64)
        }
    }
}