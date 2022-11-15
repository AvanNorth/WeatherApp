package com.example.weatherapp.data.api

import com.example.weatherapp.data.api.model.WeatherUnlocked
import com.example.weatherapp.data.api.model.WeatherUnlockedForecast
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("weather?")
    suspend fun getWeather(@Query("q") cityName: String): WeatherUnlocked

    @GET("current/{lat},{lon}")
    suspend fun getWeather(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double
    ): WeatherUnlocked

    @GET("forecast/{lat},{lon}")
    suspend fun getWeatherForecast(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double
    ): WeatherUnlockedForecast
}