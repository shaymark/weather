package com.markoapps.weather.models

import com.google.gson.annotations.SerializedName


data class ForecastResponse (
    @SerializedName("list") val list: List<ForecastModel>
        )

data class ForecastModel (
        @SerializedName("dt") val dateTime: Long,
        @SerializedName("main") val main: Main,
        @SerializedName("weather") val weather: List<Weather>,
        )

