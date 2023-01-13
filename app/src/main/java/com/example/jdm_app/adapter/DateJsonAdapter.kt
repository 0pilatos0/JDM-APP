package com.example.jdm_app.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateJsonAdapter {
    private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @ToJson
    fun toJson(date: LocalDate): String {
        return date.format(FORMATTER)
    }

    @FromJson
    fun fromJson(string: String): LocalDate {
        return LocalDate.parse(string, FORMATTER)
    }
}

