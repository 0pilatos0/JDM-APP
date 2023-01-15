package com.example.jdm_app.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jdm_app.databinding.ReservationDetailBinding
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.service.ReservationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ReservationDetailBinding

    /**
     * Called when the activity is starting.
     * 1. Inflate the `ReservationDetailBinding` layout.
     * 2. Set the content view to the root of the binding.
     * 3. Retrieve the passed in `Car` object from the intent extra
     * 4. Call the method `bindReservationData(reservation)` to populate the views with the car data
     * 5. Call the method `setupBackButton()` to setup the back button functionality.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReservationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservation = intent.getSerializableExtra("reservation") as Reservation
        bindReservationData(reservation)
        setupEditButton(reservation)
        setupBackButton()
    }

    /**
     * Binds the properties of the provided `reservation` object to the corresponding views in the layout file.
     *
     * @param reservation The Reservation object whose properties will be used to populate the view.
     */
    private fun bindReservationData(reservation: Reservation) {
        // Car information
        binding.textViewTitle.text = "${reservation.carListing?.color} ${reservation.carListing?.brand}"
        binding.textViewDescription.text = "${reservation.carListing?.description}"
        binding.textViewCarType.text = "Car type: ${reservation.carListing?.carType}"
        binding.textViewLicensePlate.text = "License plate: ${reservation.carListing?.licensePlate}"
        binding.textViewPrice.text = "Price: ${reservation.carListing?.price}"
        binding.textViewCostPerKilometer.text = "Cost per kilometer: ${reservation.carListing?.costPerKilometer}"
        binding.textViewSeats.text = "Seats: ${reservation.carListing?.seats}"

        // Reservation information
        binding.textViewReservationDate.text = "Reservation date: ${reservation.reservationDate}"
        binding.textViewRentDate.text = "Rent date: ${reservation.rentConditions?.rentDate}"
        binding.textViewReturnDate.text = "Return date: ${reservation.returnDate}"
        binding.textViewPostalCode.text = "Postal Code: ${reservation.rentConditions?.postalCode}"
        binding.textViewHouseNumber.text = "House number: ${reservation.rentConditions?.houseNumber}"
        binding.textViewReservationFinal.text = "Reservation finalised: ${if (reservation.reservationFinal!!) "Yes" else "No"}"

        binding.textViewTermsCondition.text = "Terms and conditions: ${reservation.termsAndConditions}"

        binding.buttonEdit.text = "${if (reservation.reservationFinal!!) "Delete" else "Edit"}"
        binding.buttonEdit.setBackgroundColor(Color.parseColor(if (reservation.reservationFinal!!) "#FFB33A3A" else "#FF4CAF50"))
    }

    /**
     * Setups up the back button by adding a click listener that closes the current activity when clicked.
     */
    private fun setupEditButton(reservation: Reservation) {
        binding.buttonEdit.setOnClickListener {
            if (binding.buttonEdit.text == "Edit") {
                val intent = Intent(this, ReservationEditActivity::class.java)
                intent.putExtra("reservation", reservation)
                this.startActivity(intent)

            } else {
                if (reservation.id == null) {
                    finish()
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        val response =
                            ReservationApi.retrofitService.deleteReservation(reservation.id)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@ReservationDetailActivity,
                                    "Reservation deleted!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@ReservationDetailActivity,
                                    "Error occurred while deleting reservation!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
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
