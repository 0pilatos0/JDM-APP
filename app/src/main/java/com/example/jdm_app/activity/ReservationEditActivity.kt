package com.example.jdm_app.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jdm_app.databinding.ReservationEditBinding
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.domain.RentCondition
import com.example.jdm_app.service.ReservationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class ReservationEditActivity : AppCompatActivity() {

    private lateinit var binding: ReservationEditBinding
    private lateinit var reservation: Reservation
    private lateinit var rentCondition: RentCondition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReservationEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reservation = intent.getSerializableExtra("reservation") as Reservation
        rentCondition = RentCondition()

        binding.editDateReservationDate.setText(reservation.reservationDate.toString())
        binding.editDateReturnDate.setText(reservation.termsAndConditions)
        binding.editBoolReservationFinal.setText(reservation.reservationFinal.toString())
        binding.editTextTermsConditions.setText(reservation.termsAndConditions)

        binding.editDateRentDate.setText(rentCondition.rentDate.toString())
        binding.editTextPostalCode.setText(rentCondition.postalCode)
        binding.editTextHouseNumber.setText(rentCondition.houseNumber)

        binding.buttonSave.setOnClickListener {
            reservation.reservationDate = binding.editDateReservationDate.text.toString() as LocalDate
            reservation.returnDate = binding.editDateReturnDate.text.toString() as LocalDate
            reservation.reservationFinal = binding.editDateRentDate.text.toString() as Boolean
            reservation.termsAndConditions = binding.editTextTermsConditions.text.toString()

            rentCondition.rentDate = binding.editDateRentDate.text.toString() as LocalDate
            rentCondition.postalCode = binding.editTextPostalCode.text.toString()
            rentCondition.houseNumber = binding.editTextHouseNumber.text.toString()

            CoroutineScope(Dispatchers.IO).launch {

                val response = ReservationApi.retrofitService.createReservation(reservation)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ReservationEditActivity, "Car rented!", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@ReservationEditActivity,
                            "Error occurred while renting car!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.buttonCancel.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = ReservationApi.retrofitService.deleteReservationById(reservation)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ReservationEditActivity, "Reservation canceled!", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@ReservationEditActivity,
                            "Error occurred while canceling reservation!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}