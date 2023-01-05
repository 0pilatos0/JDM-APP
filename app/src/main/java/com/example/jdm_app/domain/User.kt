package com.example.jdm_app.domain

import com.squareup.moshi.Json
import java.io.Serializable

data class User(
    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "username")
    val username: String? = null,

    @Json(name = "dateOfBirth")
    val dateOfBirth: String? = null,

    @Json(name = "address")
    val address: String? = null,

    @Json(name = "phoneNumber")
    val phoneNumber: String? = null,
) : Serializable
