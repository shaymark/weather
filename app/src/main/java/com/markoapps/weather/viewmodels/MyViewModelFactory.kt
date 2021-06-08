package com.markoapps.weather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.markoapps.weather.networks.WeatherApi
import com.markoapps.weather.repositories.WeatherRepository
import com.markoapps.weather.utils.LocationUtil
import com.markoapps.weather.utils.TimeUtil
import javax.inject.Inject

class MyViewModelFactory @Inject constructor(val weatherRepository: WeatherRepository, val weatherApi: WeatherApi, val locationUtil: LocationUtil, val timeUtil: TimeUtil)
    :  ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)){
            return  CurrentWeatherViewModel(weatherRepository, weatherApi, locationUtil, timeUtil) as T
        }
        if(modelClass.isAssignableFrom(CitiesViewModel::class.java)){
            return  CitiesViewModel(weatherApi, locationUtil) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}