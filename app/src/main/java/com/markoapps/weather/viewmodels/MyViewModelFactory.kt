package com.markoapps.weather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.markoapps.weather.di.Providers

class WeatherViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)){
            return  CurrentWeatherViewModel(Providers.weatherApi, Providers.locationUtil, Providers.timeUtil) as T
        }
        if(modelClass.isAssignableFrom(CitiesViewModel::class.java)){
            return  CitiesViewModel(Providers.weatherApi, Providers.locationUtil) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}