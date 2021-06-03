package com.markoapps.weather.convertors

enum class TempetureType {
    Celsius,
    Ferenite
}

fun Float.toTemprature(type: TempetureType): Float =
    when (type) {
        TempetureType.Celsius -> this - 273.15
        TempetureType.Ferenite -> (this - 273.15) * 9 / 5 + 32
    }.toFloat()

fun Float.toTempratureText(type: TempetureType): String =
    "%.0f".format(toTemprature(type)) +
            when(type)
            {
                TempetureType.Celsius ->  "C"
                TempetureType.Ferenite -> "F"
            }
