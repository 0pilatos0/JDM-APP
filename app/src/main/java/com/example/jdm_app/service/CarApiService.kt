package com.example.jdm_app.service

import com.example.jdm_app.BuildConfig
import com.example.jdm_app.domain.Car
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface CarApiService {
    @GET("car")
    suspend fun getCars(): Response<List<Car>>

    @POST("car")
    suspend fun createCar(@Body car: Car): Response<Car>

    @GET("car/user/{id}")
    suspend fun getCarsByUserId(@Path("id") id: Int): Response<List<Car>>

    @PUT("car/{id}")
    // Should be  - user_id: String
    suspend fun rentCar(@Path("id") id: Int, @Body car: Car, user_id: String): Response<Car>

    @PUT("car/{id}")
    suspend fun updateCar(@Path("id") id: Int, @Body car: Car): Response<Car>

    @DELETE("car/{id}")
    suspend fun deleteCar(@Path("id") id: Int) : Response<Void>

    @GET("car/{id}")
    suspend fun getCar(@Path("id") id: Int): Response<Car>

    @GET("car/tco/{id}")
    suspend fun getCarTCO(@Path("id") id: Int): Response<Double>

    @GET("car/cpk/{id}")
    suspend fun getCarCPK(@Path("id") id: Int): Response<Double>
}

private val BASE_URL = BuildConfig.BASE_URL

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