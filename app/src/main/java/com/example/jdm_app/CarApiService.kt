package com.example.jdm_app

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface CarApiService {
    @GET("car")
    suspend fun getCars(): Response<List<Car>>
}

private val BASE_URL = "https://c695-2a02-a450-954b-1-3ccc-e0a-a40f-c513.eu.ngrok.io/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object CarApi {
    val retrofitService: CarApiService by lazy {
        retrofit.create(CarApiService::class.java)
    }
}