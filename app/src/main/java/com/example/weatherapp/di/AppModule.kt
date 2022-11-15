package com.example.weatherapp.di

import com.example.weatherapp.data.GeoRepositoryImpl
import com.example.weatherapp.data.WeatherRepositoryImpl
import com.example.weatherapp.data.api.Api
import com.example.weatherapp.data.api.GeoCodeApi
import com.example.weatherapp.data.api.mapper.CityMapper
import com.example.weatherapp.data.api.mapper.WeatherMapper
import com.example.weatherapp.domain.repository.GeoRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GeoUseCase
import com.example.weatherapp.domain.usecase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherMapper(): WeatherMapper = WeatherMapper()

    @Provides
    @Singleton
    fun provideCityMapper(): CityMapper = CityMapper()

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: Api,
        weatherMapper: WeatherMapper
    ): WeatherRepository = WeatherRepositoryImpl(
        api = api,
        weatherMapper = weatherMapper
    )

    @Provides
    @Singleton
    fun provideGeoRepository(
        api: GeoCodeApi,
        cityMapper: CityMapper
    ): GeoRepository = GeoRepositoryImpl(
        api = api,
        cityMapper = cityMapper
    )

    @Provides
    @Singleton
    fun provideWeatherUseCase(
        weatherRepository: WeatherRepository
    ): WeatherUseCase = WeatherUseCase(
        weatherRepository = weatherRepository,
    )

    @Provides
    @Singleton
    fun provideGeoUseCase(
        geoRepository: GeoRepository
    ): GeoUseCase = GeoUseCase(
        geoRepository = geoRepository
    )
}