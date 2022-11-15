package com.example.weatherapp.data.api.mapper

import com.example.weatherapp.data.api.model.Day
import com.example.weatherapp.data.api.model.WeatherUnlocked
import com.example.weatherapp.data.api.model.WeatherUnlockedForecast
import com.example.weatherapp.domain.conventer.WindConverter
import com.example.weatherapp.domain.entity.DayWeather
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.entity.WeatherForecast


class WeatherMapper() {
    fun toWeather(response: WeatherUnlocked): Weather = Weather(
        latitude = response.lat,
        longitude = response.lon,
        temp = response.temp_c,
        windDir = response.winddir_compass,
        windSpeed = response.windspd_kmh,
        feelsLike = response.feelslike_c,
        desc = response.wx_desc,
        icon = response.wx_icon,
        humidity = response.humid_pct,
        wx_desc = response.wx_desc
    )

    private fun toDayWeather(response: Day): DayWeather = DayWeather(
        minTemp = response.temp_min_c,
        maxTemp = response.temp_max_c,
        date = response.date,
        windSpdMax = response.windspd_max_kmh,
        sunriseTime = response.sunrise_time,
        moonriseTime = response.moonrise_time
    )

    fun toWeatherList(response: WeatherUnlockedForecast): WeatherForecast = WeatherForecast(
        list = response.Days.map { day -> toDayWeather(day) }
    )
}