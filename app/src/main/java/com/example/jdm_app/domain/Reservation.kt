package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable
import java.time.LocalDate

data class Reservation(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "reservationDate")
    var reservationDate: LocalDate? = null,

    @Json(name = "returnDate")
    var returnDate: LocalDate? = null,

    @Json(name = "reservationFinal")
    var reservationFinal: Boolean? = false,

    @Json(name = "termsAndConditions")
    var termsAndConditions: String? = null,

    @Json(name = "rentConditions")
    var rentConditions: RentCondition? = null,

    @Json(name = "renter")
    var renter: Customer? = null,

    @Json(name = "carListing")
    var carListing: Car? = null,

    ) : Serializable