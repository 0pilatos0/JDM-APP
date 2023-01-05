package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable

data class Car(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "licensePlate")
    var licensePlate: String,

    @Json(name = "brand")
    var brand: String,

    @Json(name = "carType")
    var carType: String,

    @Json(name = "color")
    var color: String,

    @Json(name = "price")
    var price: Int,

    @Json(name = "costPerKilometer")
    var costPerKilometer: Double,

    @Json(name = "seats")
    var seats: Int,

    @Json(name = "owner")
    val owner: User? = null,

    @Json(name = "description")
    var description: String,

    @Json(name = "images")
    val images: List<String>,


) : Serializable