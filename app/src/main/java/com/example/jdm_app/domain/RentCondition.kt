package com.example.jdm_app.domain

import com.example.jdm_app.adapter.DateJsonAdapter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import java.io.Serializable
import java.util.*

data class RentCondition(
    @Json(name = "id")
    val id: Int? = null,

    @JsonAdapter(DateJsonAdapter::toJson)
    var rentDate: Date? = null,

    @Json(name = "postalCode")
    var postalCode: String? = null,

    @Json(name = "houseNumber")
    var houseNumber: String? = null,

    ) : Serializable
