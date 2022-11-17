package com.example.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.entity.WeatherList
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import timber.log.Timber

class SearchCityAdapter(
    private var weatherList: WeatherList,
    private var context: Context
) :
    RecyclerView.Adapter<SearchCityAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView){

    val cityTV: TextView = itemView.findViewById(R.id.searchCityTV)
    val countryTV: TextView = itemView.findViewById(R.id.searchCountryTV)
    val tempTV: TextView = itemView.findViewById(R.id.searchCityTempTV)
    val windTV: TextView = itemView.findViewById(R.id.searchCityWindTV)
    val wxTV: TextView = itemView.findViewById(R.id.searchCityWxTV)
    val wxIcoTV: ImageView = itemView.findViewById(R.id.searchCityWxIcoIV)
    val list: RecyclerView = itemView.findViewById(R.id.searchWeekTempList)
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
    val itemView =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.city_search_item, parent, false)
    return WeatherViewHolder(itemView)
}

override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
    with(context) {
        holder.cityTV.text = getKeys().elementAt(position).cityName
        holder.countryTV.text = getKeys().elementAt(position).country
        holder.tempTV.text =
            getString(
                R.string.temp_text,
                weatherList.list[getKeys().elementAt(position)]?.temp
            )
        holder.windTV.text =
            getString(
                R.string.wind_spd_text,
                weatherList.list[getKeys().elementAt(position)]?.windSpeed
            )
        holder.wxTV.text = weatherList.list[getKeys().elementAt(position)]?.wx_desc

        holder.list.adapter = weatherList.forecastList[getKeys().elementAt(position)]?.let {
            WeatherAdapter(
                it, context
            )
        }

        holder.list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        Picasso.get()
            .load(
                "http://www.weatherunlocked.com/Images/icons/2/${
                    weatherList.list[getKeys().elementAt(position)]?.icon?.replace(
                        ".gif",
                        ".png"
                    )
                }"
            ).fit()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.wxIcoTV, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) {
                    Timber.e("Picasso", e?.message.toString())
                }
            })
    }
}

override fun getItemCount() = weatherList.list.size

private fun getKeys() = weatherList.list.keys
}