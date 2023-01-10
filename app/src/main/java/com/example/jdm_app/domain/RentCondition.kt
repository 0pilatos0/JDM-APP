package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.Date

data class RentCondition(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "rentDate")
    var rentDate: Date? = null,

    @Json(name = "postalCode")
    var postalCode: Int? = null,

    @Json(name = "houseNumber")
    var houseNumber: Int? = null,

    ) : Serializable