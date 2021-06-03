package com.markoapps.weather.repositories


import com.markoapps.weather.models.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherRepository() {

    private val currentcityWeatherStateFlow: MutableStateFlow<List<WeatherModel>> = MutableStateFlow(listOf())
    val currentcityWeather: StateFlow<List<WeatherModel>> = currentcityWeatherStateFlow

    fun getWeatherinCurrentLocation(lon: Double, lat: Double){

    }

    suspend fun refreshCity(cityId: Int){
//        currentcityWeatherStateFlow.value = weather
    }

    fun refreshCitiesNearMe() {

    }

    fun addCityToBlackList() {

    }

    fun clearBlackList() {

    }

    fun get5DaysForcast() {

    }

}