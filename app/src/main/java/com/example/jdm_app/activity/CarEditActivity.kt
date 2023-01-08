package com.example.jdm_app.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.ArrayAdapter
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
        bindCarData()
        setupSpinners()
        setupNavigation()
        setupButtons()
    }

    private fun bindCarData() {
        binding.editTextColor.setText(car.color)
        binding.editTextLicensePlate.setText(car.licensePlate)
        binding.editTextPrice.setText(car.price.toString())
        binding.editTextCostPerKilometer.setText(car.costPerKilometer.toString())
        binding.editTextSeats.setText(car.seats.toString())
        binding.editTextDescription.setText(car.description)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewCarImages.layoutManager = layoutManager
        binding.recyclerViewCarImages.adapter = ImageAdapter(car.images as MutableList<String>)
    }

    private fun setupSpinners() {
        val brandAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.car_brands,
            android.R.layout.simple_spinner_item
        )
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editSpinnerCarBrand.adapter = brandAdapter
        val brandPosition = brandAdapter.getPosition(car.brand)
        binding.editSpinnerCarBrand.setSelection(brandPosition)

        val typeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.car_types,
            android.R.layout.simple_spinner_item
        )
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editSpinnerCarType.adapter = typeAdapter
        val typePosition = typeAdapter.getPosition(car.carType)
        binding.editSpinnerCarType.setSelection(typePosition)
    }

    private fun setupNavigation() {
        binding.bottomCarEditNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    if (car.id == null) {
                        finish()
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        val response = CarApi.retrofitService.deleteCar(car.id!!)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@CarEditActivity, "Car deleted!", Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@CarEditActivity,
                                    "Error occurred while deleting car!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    true
                }

                R.id.action_cancel -> {
                    finish()
                    true
                }

                R.id.action_save -> {
                    car.brand = binding.editSpinnerCarBrand.selectedItem.toString()
                    car.color = binding.editTextColor.text.toString()
                    car.carType = binding.editSpinnerCarType.selectedItem.toString()
                    car.licensePlate = binding.editTextLicensePlate.text.toString()
                    car.price = binding.editTextPrice.text.toString().toInt()
                    car.costPerKilometer =
                        binding.editTextCostPerKilometer.text.toString().toDouble()
                    car.seats = binding.editTextSeats.text.toString().toInt()
                    car.description = binding.editTextDescription.text.toString()

                    CoroutineScope(Dispatchers.IO).launch {
                        if (car.id == null) {
                            val response = CarApi.retrofitService.createCar(car)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        this@CarEditActivity, "Car created!", Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@CarEditActivity,
                                        "Error occurred while creating car!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            val response = CarApi.retrofitService.updateCar(car.id!!, car)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        this@CarEditActivity, "Car updated!", Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@CarEditActivity,
                                        "Error occurred while updating car!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun setupButtons() {
        binding.buttonSelectImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
        }

        binding.buttonClearImages.setOnClickListener {
            car.images = mutableListOf()
            binding.recyclerViewCarImages.adapter?.notifyDataSetChanged()
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
            binding.recyclerViewCarImages.adapter?.notifyDataSetChanged()
        }
    }

}
