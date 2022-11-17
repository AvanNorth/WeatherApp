package com.example.weatherapp.data.api

import com.example.weatherapp.data.api.model.CityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeApi {
    @GET("reverse?")
    suspend fun getCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int = 5,
        ): CityResponse

    @GET("direct?")
    suspend fun getCity(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        ): CityResponse
}