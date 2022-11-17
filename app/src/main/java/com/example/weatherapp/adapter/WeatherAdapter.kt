package com.example.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.entity.WeatherForecast

class WeatherAdapter(private val weatherForecast: WeatherForecast, private val context: Context) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val minTempTV: TextView = itemView.findViewById(R.id.minTempTV)
        val maxTempTV: TextView = itemView.findViewById(R.id.maxTempTV)
        val date: TextView = itemView.findViewById(R.id.searchCityTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.week_temp_item, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        with(context) {
            holder.minTempTV.text =
                getString(R.string.temp_text, weatherForecast.list[position].minTemp)
            holder.maxTempTV.text =
                getString(R.string.temp_text, weatherForecast.list[position].maxTemp)
            holder.date.text = weatherForecast.list[position].date
        }
    }

    override fun getItemCount() = weatherForecast.list.size
}