package com.markoapps.weather.models

import com.google.gson.annotations.SerializedName

data class ForcastResponse (
    @SerializedName("list") val list: List<ForcastModel>
        )

data class ForcastModel (
        @SerializedName("dt") val dateTime: Double,
        @SerializedName("main") val main: Main,
        @SerializedName("weather") val weather: List<Weather>
        )

