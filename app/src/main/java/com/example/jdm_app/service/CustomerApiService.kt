package com.example.jdm_app.service

import com.example.jdm_app.BuildConfig
import com.example.jdm_app.domain.Customer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface CustomerApiService {

    @POST("customer")
    suspend fun createCustomer(@Body customer: Customer): Response<Customer>

    @GET("customer/{id}")
    suspend fun getCustomer(@Path("id") id: Int): Response<Customer>

    @PUT("customer/{id}")
    suspend fun updateCustomer(@Path("id") id: Int, @Body customer: Customer): Response<Customer>

    @DELETE("customer/{id}")
    suspend fun deleteCustomer(@Path("id") id: Int): Response<Void>

}

private const val BASE_URL = BuildConfig.BASE_URL

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

object CustomerApi {
    val retrofitService: CustomerApiService by lazy {
        retrofit.create(CustomerApiService::class.java)
    }
}