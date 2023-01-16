package com.example.jdm_app.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true) @Json(name = "id") var id: Int? = null,

    @Json(name = "username") var username: String? = null,

    @Json(name = "dateOfBirth") var dateOfBirth: String? = null,

    @Json(name = "address") var address: String? = null,

    @Json(name = "phoneNumber") var phoneNumber: String? = null,
) : Serializable
