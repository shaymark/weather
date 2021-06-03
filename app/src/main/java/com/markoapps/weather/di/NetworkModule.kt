package com.markoapps.weather.di

import com.markoapps.weather.networks.WeatherApi
import com.markoapps.weather.networks.WeatherApiBuilder
import com.markoapps.weather.utils.Constans
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


@Module
class NetworkModule {

    @Provides
    fun weatherApi(): WeatherApi {
        return WeatherApiBuilder().getWeatherApi()
    }

}