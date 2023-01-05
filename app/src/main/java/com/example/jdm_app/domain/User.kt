package com.example.jdm_app.domain

import com.squareup.moshi.Json

data class User(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "username")
    val username: String,

    @Json(name = "dateOfBirth")
    val dateOfBirth: String,

    @Json(name = "address")
    val address: String,

    @Json(name = "phoneNumber")
    val phoneNumber: String,
)
