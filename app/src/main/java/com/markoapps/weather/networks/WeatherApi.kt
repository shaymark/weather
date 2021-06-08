package com.markoapps.weather.networks

import com.markoapps.weather.models.FindResponse
import com.markoapps.weather.models.ForecastResponse
import com.markoapps.weather.models.WeatherModel
import com.markoapps.weather.viewmodels.CitiesViewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeatherByCity(
            @Query("q") query: String
    ): WeatherModel

    @GET("weather")
    fun getCurrentWeatherByCityFlow(
        @Query("q") query: String
    ): Flow<WeatherModel>

    @GET("weather")
    suspend fun getCurrentWeatherByCityId(
        @Query("id") id: String
    ): WeatherModel

    @GET("weather")
    suspend fun getCurrentWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): WeatherModel

    @GET("weather")
    fun getCurrentWeatherByLocationFlow(
            @Query("lat") lat: String,
            @Query("lon") lon: String
    ): Flow<WeatherModel>

    @GET("forecast")
    suspend fun getForecastByCity(
        @Query("q") query: String
    ): ForecastResponse

    @GET("forecast")
    suspend fun getForecastByCityId(
        @Query("id") id: String
    ): ForecastResponse

    @GET("forecast")
    suspend fun getForecastByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): ForecastResponse

    @GET("find")
    suspend fun getCitiesByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("cnt") count: Int = 50
    ): FindResponse

    @GET("find")
    suspend fun getCitiesByName(
        @Query("q") name: String,
        @Query("cnt") count: Int = 50
    ): FindResponse

}