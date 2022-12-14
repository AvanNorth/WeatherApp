package com.example.weatherapp.data.api.model

import com.google.gson.annotations.SerializedName

data class WeatherUnlocked(
    val alt_ft: Double,
    val alt_m: Double,
    val cloudtotal_pct: Double,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val humid_pct: Double,
    val lat: Double,
    val lon: Double,
    val slp_in: Double,
    val slp_mb: Double,
    val temp_c: Double,
    val temp_f: Double,
    val vis_desc: Any,
    val vis_km: Double,
    val vis_mi: Double,
    val winddir_compass: String,
    val winddir_deg: Double,
    val windspd_kmh: Double,
    val windspd_kts: Double,
    val windspd_mph: Double,
    val windspd_ms: Double,
    val wx_code: Int,
    val wx_desc: String,
    val wx_icon: String
)