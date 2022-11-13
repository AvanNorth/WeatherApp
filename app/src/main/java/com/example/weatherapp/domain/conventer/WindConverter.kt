package com.example.weatherapp.domain.conventer

class WindConverter {
    fun convertWindDir(windDeg: Int): String {
        return when (windDeg) {
            in 0..22 -> "N"
            in 23..67 -> "NE"
            in 68..112 -> "E"
            in 113..157 -> "SE"
            in 158..202 -> "S"
            in 203..247 -> "SW"
            in 248..292 -> "W"
            in 293..337 -> "NW"
            in 337..360 -> "N"
            else -> "Not found"
        }
    }
}