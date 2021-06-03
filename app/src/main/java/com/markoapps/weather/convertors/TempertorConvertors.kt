package com.markoapps.weather.convertors

enum class TemperatureType {
    Celsius,
    Fahrenheit
}

fun Float.toTemperature(type: TemperatureType): Float =
    when (type) {
        TemperatureType.Celsius -> this - 273.15
        TemperatureType.Fahrenheit -> (this - 273.15) * 9 / 5 + 32
    }.toFloat()

fun Float.toTemperatureText(type: TemperatureType): String =
    "%.0f".format(toTemperature(type)) +
            when(type)
            {
                TemperatureType.Celsius ->  "C"
                TemperatureType.Fahrenheit -> "F"
            }
