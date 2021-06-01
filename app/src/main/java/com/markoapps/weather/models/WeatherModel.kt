package com.markoapps.weather.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        @SerializedName("coord") val coord: Coord,
        @SerializedName("weather") val weather: WeatherModel
)

data class WeatherModel (
    @SerializedName("id")   val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("coord")val coord: Coord,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("sys") val sys: Sys,

    )

data class Coord (
    @SerializedName("lat") val lat: Float,
    @SerializedName("lon") val lon: Float
    )

data class Main (
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val FeelsLike: Float,
    @SerializedName("temp_min") val TempMin: Float,
    @SerializedName("temp_max") val TempMax: Float,
    @SerializedName("pressure") val pressure: Float,
    @SerializedName("humidity") val humidity: Float,
        )

data class Wind (
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val deg: Int,
        )

data class Sys (
    @SerializedName("type") val type: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Double,
    @SerializedName("sunset") val sunset: Double
)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String,
)