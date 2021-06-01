package com.markoapps.weather.networks

import com.markoapps.weather.models.ForcastResponse
import com.markoapps.weather.models.Weather
import com.markoapps.weather.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeatherByCity(
            @Query("q") query: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByCityId(
        @Query("id") id: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): WeatherResponse

    @GET("forecast")
    suspend fun getForecastByCity(
        @Query("q") query: String
    ): ForcastResponse

    @GET("forecast")
    suspend fun getForecastByCityId(
        @Query("id") id: String
    ): ForcastResponse

    @GET("forecast")
    suspend fun getForecastByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): ForcastResponse

}