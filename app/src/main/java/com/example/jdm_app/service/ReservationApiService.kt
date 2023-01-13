package com.example.jdm_app.service

import com.example.jdm_app.BuildConfig
import com.example.jdm_app.adapter.DateJsonAdapter
import com.example.jdm_app.domain.Reservation
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*

interface ReservationApiService {
    @GET("reservation/{id}")
    suspend fun getReservationsByUserId(@Path("id") id: Int): Response<Reservation>

    @PUT("reservation/{id}")
    suspend fun createReservation(@Body reservation: Reservation): Response<Reservation>

    @PUT("reservation/{id}")
    suspend fun updateReservation(@Path("id") id: Int, @Body reservation: Reservation): Response<Reservation>

    @DELETE("reservation/{id}")
    suspend fun deleteReservationById(reservation: Reservation): Response<Void>
}

private val BASE_URL = BuildConfig.BASE_URL

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(Date::class.java, DateJsonAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object ReservationApi {
    val retrofitService: ReservationApiService by lazy {
        retrofit.create(ReservationApiService::class.java)
    }
}