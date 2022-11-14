package com.example.weatherapp.data.api.model

import com.example.weatherapp.data.api.model.WeatherResponse
import com.google.gson.annotations.SerializedName

class WeatherResponseList(
    @SerializedName("list")
    val weatherList: List<WeatherResponse>
)