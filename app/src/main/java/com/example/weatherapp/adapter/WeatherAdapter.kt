package com.example.weatherapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.entity.Cities

class WeatherAdapter(private val cities: Cities) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityNameTV: TextView = itemView.findViewById(R.id.name_tv)
        val cityTempTV: TextView = itemView.findViewById(R.id.tempTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.cityNameTV.text = cities.list[position].name
        holder.cityTempTV.text = "температура: ${cities.list[position].temp}"
        holder.cityTempTV.setTextColor(Color.parseColor(getTempColor(cities.list[position].temp)))
    }

    private fun getTempColor(temp: Double): String {
        when (temp) {
            in -60.0..-30.0 -> return "#0313fc"
            in -29.9..-15.0 -> return "#2596be"
            in -14.9..0.0 -> return "#03f8fc"
            in 0.1..15.0 -> return "#1cfc03"
            in 15.1..29.9 -> return "#f4fc03"
            in 30.0..60.0 -> return "#fc2403"
        }
        return "#000000"
    }

    override fun getItemCount() = 10
}