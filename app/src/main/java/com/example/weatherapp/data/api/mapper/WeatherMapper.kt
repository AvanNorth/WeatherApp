package com.example.weatherapp.data.api.mapper

import com.example.weatherapp.data.api.model.WeatherResponse
import com.example.weatherapp.data.api.model.WeatherResponseList
import com.example.weatherapp.data.api.model.WeatherUnlocked
import com.example.weatherapp.domain.conventer.WindConverter
import com.example.weatherapp.domain.entity.Cities
import com.example.weatherapp.domain.entity.Weather


class WeatherMapper(
    private val windConverter: WindConverter
) {
    fun toWeather(response: WeatherUnlocked): Weather = Weather(
        latitude = response.lat,
        longitude = response.lon,
        temp = response.temp_c,
        windDir = response.winddir_compass,
        windSpeed = response.windspd_kmh,
        feelsLike = response.feelslike_c,
        desc = response.wx_desc,
        icon = response.wx_icon,
        humidity = response.humid_pct
    )

    /*fun toListWeather(response: WeatherResponseList): Cities = Cities(
        //list = response.weatherList.map { weatherInfo ->  toWeather(weatherInfo)}
    )*/
}