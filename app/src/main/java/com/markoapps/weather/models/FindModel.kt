package com.markoapps.weather.models

import com.google.gson.annotations.SerializedName

data class FindResponse (
    @SerializedName("list") val list: List<WeatherModel>
)