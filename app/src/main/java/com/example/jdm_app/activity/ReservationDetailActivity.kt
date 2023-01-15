package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jdm_app.adapter.ImageAdapter
import com.example.jdm_app.databinding.ReservationDetailBinding
import com.example.jdm_app.domain.Reservation

class ReservationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ReservationDetailBinding

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
        binding = ReservationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservation = intent.getSerializableExtra("reservation") as Reservation
        bindReservationData(reservation)
        setupEditButton()
        setupBackButton()
    }

    /**
     * Binds the properties of the provided `reservation` object to the corresponding views in the layout file.
     *
     * @param reservation The Reservation object whose properties will be used to populate the view.
     */
    private fun bindReservationData(reservation: Reservation) {
        binding.textViewTitle.text = "${reservation.carListing?.color} ${reservation.carListing?.brand}"
        binding.textViewDescription.text = "${reservation.carListing?.description}"
        binding.textViewCarType.text = "Car type: ${reservation.carListing?.carType}"
        binding.textViewLicensePlate.text = "License plate: ${reservation.carListing?.licensePlate}"
        binding.textViewPrice.text = "Price: ${reservation.carListing?.price}"
        binding.textViewCostPerKilometer.text = "Cost per kilometer: ${reservation.carListing?.costPerKilometer}"
        binding.textViewSeats.text = "Seats: ${reservation.carListing?.seats}"

        binding.textViewReturnDate.text = "${reservation.returnDate}"
        binding.textViewReservationFinal.text = "Car type: ${reservation.reservationFinal}"
        binding.textViewTermsCondition.text = "License plate: ${reservation.termsAndConditions}"
        binding.textViewRentDate.text = "Price: ${reservation.rentConditions?.rentDate}"
        binding.textViewPostalCode.text = "Cost per kilometer: ${reservation.rentConditions?.postalCode}"
        binding.textViewHouseNumber.text = "Seats: ${reservation.rentConditions?.houseNumber}"
    }

    /**
     * Setups up the back button by adding a click listener that closes the current activity when clicked.
     */
    private fun setupEditButton() {
        binding.buttonEdit.setOnClickListener {
            var reservation = Reservation()

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
}
