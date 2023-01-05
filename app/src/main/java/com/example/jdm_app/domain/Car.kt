package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable

data class Car(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "licensePlate")
    val licensePlate: String,

    @Json(name = "brand")
    val brand: String,

    @Json(name = "carType")
    val carType: String,

    @Json(name = "color")
    val color: String,

    @Json(name = "price")
    val price: Int,

    @Json(name = "costPerKilometer")
    val costPerKilometer: Double,

    @Json(name = "seats")
    val seats: Int,

    @Json(name = "description")
    val description: String,

    @Json(name = "images")
    val images: List<String>,


) : Serializable