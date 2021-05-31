package com.markoapps.weather.repositories

import com.markoapps.weather.database.WeatherDao
import com.markoapps.weather.models.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherRepository(val weatherDao: WeatherDao) {

    private val currentcityWeatherStateFlow: MutableStateFlow<List<WeatherModel>> = MutableStateFlow(listOf())
    val currentcityWeather: StateFlow<List<WeatherModel>> = currentcityWeatherStateFlow

    fun getWeatherinCurrentLocation(lon: Double, lat: Double){

    }

    suspend fun refreshCity(cityId: Int){
        val weather = weatherDao.getAllWeather().mapNotNull { it.name }
        currentcityWeatherStateFlow.value = weather
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