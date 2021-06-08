package com.markoapps.weather.repositories

import com.markoapps.weather.models.WeatherModel
import com.markoapps.weather.networks.WeatherApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository
    @Inject constructor(val weatherApi: WeatherApi) {

    fun getCurrentWeatherByCity(city: String): Flow<WeatherModel> =
            weatherApi.getCurrentWeatherByCityFlow(city)

}