package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jdm_app.adapter.ImageAdapter
import com.example.jdm_app.databinding.CarDetailBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.domain.Customer
import com.example.jdm_app.service.ReservationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarDetailActivity : AppCompatActivity() {

    private lateinit var binding: CarDetailBinding

    /**
     * Called when the activity is starting.
     * 1. Inflate the `CarDetailBinding` layout.
     * 2. Set the content view to the root of the binding.
     * 3. Retrieve the passed in `Car` object from the intent extra
     * 4. Call the method `bindCarData(car)` to populate the views with the car data
     * 5. Call the method `setupBackButton()` to setup the back button functionality.
     * 6. Call the method `setupCarImages(car.images)` to show the images of the car in the view.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val car = intent.getSerializableExtra("car") as Car
        bindCarData(car)
        setupRentButton(car)
        setupBackButton()
        setupCarImages(car.images as MutableList<String>)
    }

    /**
     * Binds the properties of the provided `car` object to the corresponding views in the layout file.
     *
     * @param car The Car object whose properties will be used to populate the view.
     */
    private fun bindCarData(car: Car) {
        binding.textViewTitle.text = "${car.color} ${car.brand}"
        binding.textViewDescription.text = "${car.description}"
        binding.textViewCarType.text = "Car type: ${car.carType}"
        binding.textViewLicensePlate.text = "License plate: ${car.licensePlate}"
        binding.textViewPrice.text = "Price: ${car.price}"
        binding.textViewCostPerKilometer.text = "Cost per kilometer: ${car.costPerKilometer}"
        binding.textViewSeats.text = "Seats: ${car.seats}"
    }

    /**
     * Setups up the back button by adding a click listener that closes the current activity when clicked.
     */
    private fun setupRentButton(car: Car) {
        CoroutineScope(Dispatchers.IO).launch {
            car.id?.let {
                val response = ReservationApi.retrofitService.getReservationsByCarId(it)
                if (response.isSuccessful && response.body() != null) {
                    val reservations = response.body() as List<Reservation>
                    if (reservations.isNotEmpty()) {
                        runOnUiThread {
                            binding.buttonRent.visibility = View.GONE
                        }
                    }
                }
            }
        }

        binding.buttonRent.setOnClickListener {
            var user = Customer()
            var reservation = Reservation()

            user.id = MainActivity.customer?.id
            reservation.renter = user
            reservation.carListing = car

            val intent = Intent(this, ReservationEditActivity::class.java)
            intent.putExtra("reservation", reservation)
            this.startActivity(intent)
        }
    }

    /**
     * Setups up the back button by adding a click listener that closes the current activity when clicked.
     */
    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    /**
     * Setups up the recyclerView for displaying the images of the car passed in.
     *
     * @param images A MutableList of images encoded in base64 that will be displayed in the recyclerView
     */
    private fun setupCarImages(images: MutableList<String>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewCarImages.layoutManager = layoutManager
        binding.recyclerViewCarImages.adapter = ImageAdapter(images)
    }
}
