package com.example.jdm_app.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class CarApiTest{

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCars should return a list of cars`() = runTest  {
      val cars = CarApi.retrofitService.getCars().body()
        advanceUntilIdle()
        assertNotNull(cars)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCarsByUserId should return a list of cars`() = runTest  {
      val cars = CarApi.retrofitService.getCarsByUserId(1).body()
        advanceUntilIdle()
        assertNotNull(cars)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCar should return a car`() = runTest  {
      val car = CarApi.retrofitService.getCar(4).body()
        advanceUntilIdle()
        assertNotNull(car)
    }
}