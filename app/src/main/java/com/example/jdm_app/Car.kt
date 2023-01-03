package com.example.jdm_app

import com.squareup.moshi.Json

data class Car(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "licensePlate")
    val userId: String,

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

    @Json(name = "owner")
    val owner: User,

    @Json(name = "seats")
    val seats: Int,

    @Json(name = "description")
    val description: String,

    @Json(name = "images")
    val images: List<String>,


)