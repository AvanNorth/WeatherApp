package com.example.weatherapp.di

import androidx.viewbinding.BuildConfig
import com.example.weatherapp.data.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val API_KEY = "27c7562ae39fc5e14083e53be2619ffe"
private const val QUERY_API_KEY = "appid"

private const val METRIC = "metric"
private const val QUERY_UNITS = "units"

private const val LANG_CODE = "en"
private const val QUERY_LANG = "lang"

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @Singleton
    @ApiKeyInterceptor
    fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Singleton
    @UnitsInterceptor
    fun provideUnitsInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, METRIC)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Singleton
    @LangInterceptor
    fun provideLangInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_LANG, LANG_CODE)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Singleton
    @HttpClient
    fun provideHttpClient(
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
        @UnitsInterceptor unitsInterceptor: Interceptor,
        @LangInterceptor langInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(unitsInterceptor)
            .addInterceptor(langInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()

    @Provides
    @Singleton
    fun provideApi(
        @HttpClient okhttp: OkHttpClient
    ): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
}

//annotations
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LangInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UnitsInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKeyInterceptor
