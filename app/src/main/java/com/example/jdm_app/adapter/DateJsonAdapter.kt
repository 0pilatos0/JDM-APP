package com.example.jdm_app.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter {
    companion object {
        private val FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    }

    @ToJson
    fun toJson(date: Date): String {
        return FORMAT.format(date)
    }

    @FromJson
    fun fromJson(string: String): Date? {
        return FORMAT.parse(string)
    }
}