package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable
import java.time.LocalDate

data class RentCondition(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "rentDate")
    var rentDate: LocalDate? = null,

    @Json(name = "postalCode")
    var postalCode: String? = null,

    @Json(name = "houseNumber")
    var houseNumber: String? = null,

    ) : Serializable
