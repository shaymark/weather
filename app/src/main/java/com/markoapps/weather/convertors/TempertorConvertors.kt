package com.markoapps.weather.convertors

enum class TempetureType {
    Celsuis,
    Ferenite
}

fun Float.convertTo(type: TempetureType): Float =
    when (type) {
        TempetureType.Celsuis -> this - 273.15
        TempetureType.Ferenite -> (this - 273.15) * 9 / 5 + 32
    }.toFloat()