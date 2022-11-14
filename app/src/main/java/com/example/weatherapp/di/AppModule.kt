package com.example.weatherapp.di

import com.example.weatherapp.data.WeatherRepositoryImpl
import com.example.weatherapp.data.api.Api
import com.example.weatherapp.data.api.mapper.WeatherMapper
import com.example.weatherapp.domain.conventer.WindConverter
import com.example.weatherapp.domain.repository.WeatherRepository
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
    fun provideWeatherMapper(): WeatherMapper = WeatherMapper(
        windConverter = WindConverter()
    )

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
    fun provideWeatherUseCase(
        weatherRepository: WeatherRepository
    ): WeatherUseCase = WeatherUseCase(
        weatherRepository = weatherRepository,
    )
}