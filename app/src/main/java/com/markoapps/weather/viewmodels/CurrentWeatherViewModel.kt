package com.markoapps.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoapps.weather.models.ForcastModel
import com.markoapps.weather.models.WeatherModel
import com.markoapps.weather.networks.WeatherApi
import com.markoapps.weather.utils.Constans
import com.markoapps.weather.utils.LocationUtil
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(val weatherApi: WeatherApi, val locationUtil: LocationUtil) : ViewModel() {

    private val _currentWeather: MutableLiveData<WeatherModel> = MutableLiveData()
    val currentWeather: LiveData<WeatherModel> = _currentWeather

    private val _forecast: MutableLiveData<List<ForcastModel>> = MutableLiveData()
    val forecast: LiveData<List<ForcastModel>> = _forecast

    fun getCurrentWeather(){
        viewModelScope.launch {
            val location = locationUtil.getLocation()

            val response = if(location != null) {
                weatherApi.getCurrentWeatherByLocation(location.longitude.toString(), location.latitude.toString() )
            } else {
                weatherApi.getCurrentWeatherByCity(Constans.defaultCityName)
            }

            _currentWeather.value = response.weather

        }
    }

    fun getWeatherForecast(){
        viewModelScope.launch {
            val location = locationUtil.getLocation()

            val response = if(location != null) {
                weatherApi.getForecastByLocation(location.longitude.toString(), location.latitude.toString() )
            } else {
                weatherApi.getForecastByCityId(Constans.defaultCityName)
            }

            _forecast.value = response.list
        }
    }
}