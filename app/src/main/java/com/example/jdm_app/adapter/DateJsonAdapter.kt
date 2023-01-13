package com.example.jdm_app.adapter

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateJsonAdapter : JsonAdapter<LocalDate>() {
    companion object {
        private val FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value.format(FORMAT))
    }

    override fun fromJson(reader: JsonReader): LocalDate? {
        val date = reader.nextString()
        return LocalDate.parse(date, FORMAT)

    }
}