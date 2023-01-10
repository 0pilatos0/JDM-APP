package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.Date

data class Reservation(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "reservationDate")
    var reservationDate: Date? = null,

    @Json(name = "returnDate")
    var returnDate: Date? = null,

    @Json(name = "reservationFinal")
    var reservationFinal: Boolean? = null,

    @Json(name = "termsAndConditions")
    var termsAndConditions: String? = null,

    @Json(name = "rentConditions")
    var rentConditions: RentCondition? = null,

    @Json(name = "renter")
    var renter: User? = null,

    @Json(name = "carListing")
    var carListing: Car? = null,

    ) : Serializable