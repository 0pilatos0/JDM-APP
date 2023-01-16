package com.example.jdm_app.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jdm_app.R
import com.example.jdm_app.adapter.LocalDateJsonAdapter
import com.example.jdm_app.databinding.ReservationEditBinding
import com.example.jdm_app.domain.RentCondition
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.service.ReservationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.jdm_app.view.CarViewModel

class ReservationEditActivity : AppCompatActivity() {

    private lateinit var binding: ReservationEditBinding
    private lateinit var reservation: Reservation
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    /**
     * Called when the activity is starting. It perform the following actions:
     *  1.  inflate the `ReservationEditBinding` layout
     *  2.  set the content view to the root of the binding
     *  3.  retrieve the passed in `Reservation` object from the intent extra
     *  4.  call the methods `bindReservationData()`, `setupNavigation()` and `setupButtons()`
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReservationEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reservation = intent.getSerializableExtra("reservation") as Reservation
        bindReservationData()
        setupNavigation()
    }

    /**
     * Binds the properties of the provided `reservation` object to the corresponding views in the layout file,
     * also set the RecyclerView layout manager and adapter
     */
    private fun bindReservationData() {
        binding.editDateReturnDate.setText(
            if (reservation.returnDate == null) ""
            else reservation.returnDate.toString()
        )
        binding.editTextTermsConditions.setText(reservation.termsAndConditions)

        binding.editDateRentDate.setText(
            if (reservation.rentConditions?.rentDate == null) ""
            else reservation.rentConditions?.rentDate.toString()
        )

        if (reservation.rentConditions?.postalCode == null) {
            val gps = getGPSLocation()
            binding.editTextPostalCode.setText(gps.toString())
        } else {
            binding.editTextPostalCode.setText(reservation.rentConditions?.postalCode)
        }

        binding.editTextHouseNumber.setText(reservation.rentConditions?.houseNumber)
    }

    private fun getGPSLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
    }

    /**
     * Setups up the navigation bar by adding click listeners to the menu items.
     * Based on the selected menu item, it performs the following actions
     *      delete - delete the reservation via API and closes the activity
     *      cancel - closes the activity without making any changes
     *      save   - update the reservation properties with the values entered in the form and via API and closes the activity.
     */
    private fun setupNavigation() {
        binding.bottomEditNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    if (reservation.id == null) {
                        finish()
                    } else{
                        CoroutineScope(Dispatchers.IO).launch {
                            val response = ReservationApi.retrofitService.deleteReservation(reservation.id!!)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        this@ReservationEditActivity, "Reservation deleted!", Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@ReservationEditActivity,
                                        "Error occurred while deleting reservation!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
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
                    reservation.reservationDate = LocalDate.now()
                    reservation.returnDate = LocalDate.parse(binding.editDateReturnDate.text.toString(), LocalDateJsonAdapter.FORMATTER)
                    reservation.termsAndConditions = binding.editTextTermsConditions.text.toString()
                    reservation.reservationFinal = binding.editCheckboxReservationFinal.isChecked

                    if (reservation.id == null) {
                        reservation.rentConditions = RentCondition()
                    }
                    reservation.rentConditions?.rentDate = LocalDate.parse(binding.editDateRentDate.text.toString(),LocalDateJsonAdapter.FORMATTER)
                    reservation.rentConditions?.postalCode = binding.editTextPostalCode.text.toString()
                    reservation.rentConditions?.houseNumber = binding.editTextHouseNumber.text.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        if (reservation.id == null) {
                            val response = ReservationApi.retrofitService.createReservation(reservation)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(this@ReservationEditActivity, "Car rented!", Toast.LENGTH_SHORT)
                                        .show()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@ReservationEditActivity,
                                        "Error occurred while renting the car!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            val response = ReservationApi.retrofitService.updateReservation(reservation.id!!, reservation)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        this@ReservationEditActivity, "Reservation updated!", Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@ReservationEditActivity,
                                        "Error occurred while updating reservation!",
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
}
