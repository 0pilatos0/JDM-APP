package com.example.jdm_app.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
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
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.jdm_app.view.CarViewModel
import java.text.SimpleDateFormat
import java.util.*

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
        binding.editTextTermsConditions.setText(reservation.termsAndConditions)

        if (reservation.rentConditions?.postalCode == null) {
//            getGPSLocation()
        } else {
            binding.editTextPostalCode.setText(reservation.rentConditions?.postalCode)
        }

        binding.getLocation.setOnClickListener {
            getGPSLocation()
        }

        binding.editTextHouseNumber.setText(reservation.rentConditions?.houseNumber)
    }


    private fun getGPSLocation() {
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Request location permission
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode
            )

        } else {
            // Permission granted, get location
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (location != null) {
                // Get postal code from location
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val postalCode = addresses?.get(0)?.postalCode

                if (postalCode != null) {
                    // Set postal code in EditText
                    binding.editTextPostalCode.setText(postalCode)
                } else {
                    // postal code not found, show error message
                    Toast.makeText(this, "Postal code not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Location not found, show error message
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getGPSLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
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
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            val response =
                                ReservationApi.retrofitService.deleteReservation(reservation.id!!)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        this@ReservationEditActivity,
                                        "Reservation deleted!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                    val intent = Intent(
                                        this@ReservationEditActivity,
                                        ReservationActivity::class.java
                                    )
                                    startActivity(intent)
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
                    val day: Int = binding.editDateReturnDate.getDayOfMonth()
                    val month: Int = binding.editDateReturnDate.getMonth()
                    val year: Int = binding.editDateReturnDate.getYear()
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.set(year, month, day)

                    val date = SimpleDateFormat("yyyy-MM-dd")
                    val formatedDate: String = date.format(calendar.time)

                    reservation.returnDate =
                        LocalDate.parse(formatedDate, LocalDateJsonAdapter.FORMATTER)

                    reservation.termsAndConditions = binding.editTextTermsConditions.text.toString()
                    reservation.reservationFinal = binding.editCheckboxReservationFinal.isChecked

                    if (reservation.id == null) {
                        reservation.rentConditions = RentCondition()
                    }
                    val rentDay: Int = binding.editDateRentDate.getDayOfMonth()
                    val rentMonth: Int = binding.editDateRentDate.getMonth()
                    val rentYear: Int = binding.editDateRentDate.getYear()
                    val rentCalendar: Calendar = Calendar.getInstance()
                    rentCalendar.set(rentYear, rentMonth, rentDay)

                    val rentDate = SimpleDateFormat("yyyy-MM-dd")
                    val formatedRentDate: String = rentDate.format(rentCalendar.time)

                    reservation.rentConditions?.rentDate =
                        LocalDate.parse(formatedRentDate, LocalDateJsonAdapter.FORMATTER)

                    reservation.rentConditions?.postalCode =
                        binding.editTextPostalCode.text.toString()
                    reservation.rentConditions?.houseNumber =
                        binding.editTextHouseNumber.text.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        if (reservation.id == null) {
                            val response =
                                ReservationApi.retrofitService.createReservation(reservation)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        this@ReservationEditActivity,
                                        "Car rented!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(
                                        this@ReservationEditActivity,
                                        ReservationDetailActivity::class.java
                                    )
                                    intent.putExtra("reservation", reservation)
                                    this@ReservationEditActivity.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this@ReservationEditActivity,
                                        "Error occurred while renting the car!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            val response = ReservationApi.retrofitService.updateReservation(
                                reservation.id!!, reservation
                            )
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        this@ReservationEditActivity,
                                        "Reservation updated!",
                                        Toast.LENGTH_SHORT
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
