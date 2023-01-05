package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable

data class Car(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "licensePlate")
    var licensePlate: String? = null,

    @Json(name = "brand")
    var brand: String? = null,

    @Json(name = "carType")
    var carType: String? = null,

    @Json(name = "color")
    var color: String? = null,

    @Json(name = "price")
    var price: Int? = 0,

    @Json(name = "costPerKilometer")
    var costPerKilometer: Double? = 0.0,

    @Json(name = "seats")
    var seats: Int? = 0,

    @Json(name = "owner")
    var owner: User? = null,

    @Json(name = "description")
    var description: String? = null,

    @Json(name = "images")
    val images: MutableList<String>? = mutableListOf(),


    ) : Serializable