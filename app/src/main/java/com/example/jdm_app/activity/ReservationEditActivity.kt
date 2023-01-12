package com.example.jdm_app.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jdm_app.databinding.RentedCarEditBinding
import com.example.jdm_app.domain.Reservation
import com.example.jdm_app.domain.RentCondition
import com.example.jdm_app.service.ReservationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationEditActivity : AppCompatActivity() {

    private lateinit var binding: RentedCarEditBinding
    private lateinit var reservation: Reservation
    private lateinit var rentCondition: RentCondition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RentedCarEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editDateReservationDate.setDate(reservation.reservationDate)
        binding.editDateReturnDate.setDate(reservation.termsAndConditions)
        binding.editBoolReservationFinal.boolean(reservation.reservationFinal)
        binding.editTextTermsConditions.setText(reservation.termsAndConditions)

        binding.editDateRentDate.setDate(rentCondition.rentDate)
        binding.editTextPostalCode.setText(rentCondition.postalCode)
        binding.editTextHouseNumber.setText(rentCondition.houseNumber)

        binding.buttonSave.setOnClickListener {
            reservation.reservationDate = binding.editDateReservationDate.date.toString()
            reservation.returnDate = binding.editDateReturnDate.date.toString()
            reservation.reservationFinal = binding.editDateRentDate.date.toString()
            reservation.termsAndConditions = binding.editTextTermsConditions.text.toString()

            rentCondition.rentDate = binding.editDateRentDate.date.toString()
            rentCondition.postalCode = binding.editTextPostalCode.text.toString()
            rentCondition.houseNumber = binding.editTextHouseNumber.text.toString()
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