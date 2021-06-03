package com.markoapps.weather.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoapps.weather.convertors.TempetureType
import com.markoapps.weather.models.ForecastModel
import com.markoapps.weather.models.WeatherModel
import com.markoapps.weather.networks.WeatherApi
import com.markoapps.weather.utils.Constans
import com.markoapps.weather.utils.LocationUtil
import com.markoapps.weather.utils.TimeUtil
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class ValueWrapper<T>(private val value: T) {

    private var isConsumed = false

    fun get(): T? =
        if (isConsumed) {
            null
        } else {
            isConsumed = true
            value
        }
}

class CurrentWeatherViewModel(val weatherApi: WeatherApi, val locationUtil: LocationUtil, val timeUtil: TimeUtil) : ViewModel() {

    companion object {
        val TAG: String = CurrentWeatherViewModel::class.java.simpleName
    }

    private val _mode: MutableLiveData<TempetureType> = MutableLiveData()
    val mode: LiveData<TempetureType> = _mode

    private val _currentTime: MutableLiveData<Date> = MutableLiveData()
    val currentTime: LiveData<Date> = _currentTime

    private val _currentWeather: MutableLiveData<WeatherModel> = MutableLiveData()
    val currentWeather: LiveData<WeatherModel> = _currentWeather

    private val _forecast: MutableLiveData<List<ForecastModel>> = MutableLiveData()
    val forecast: LiveData<List<ForecastModel>> = _forecast

    init {
        _mode.value = TempetureType.Celsius
    }

    fun refreshCurrentWeather(){
        viewModelScope.launch {
            val location = locationUtil.getLocation()

            try {
                val response = if(location != null) {
                    weatherApi.getCurrentWeatherByLocation(location.latitude.toString(), location.longitude.toString() )
                } else {
                    weatherApi.getCurrentWeatherByCity(Constans.defaultCityName)
                }

                _currentTime.value = timeUtil.getCurrentTime()
                _currentWeather.value = response

            } catch (e: Exception) {
                Log.d(TAG, "refreshCurrentWeather exception: ${e.message}")
            }
        }
    }

    fun refreshWeatherForecast(){
        viewModelScope.launch {
            val location = locationUtil.getLocation()

            try {
                val response = if(location != null) {
                    weatherApi.getForecastByLocation(location.latitude.toString(), location.longitude.toString() )
                } else {
                    weatherApi.getForecastByCity(Constans.defaultCityName)
                }

                _forecast.value = response.list
            } catch (e: Exception) {
                Log.d(TAG, "refreshWeatherForecast exception: ${e.message}")
            }

        }
    }

    fun toggleMode() {
        when(_mode.value) {
            TempetureType.Celsius -> {
                _mode.value = TempetureType.Ferenite
            }
            TempetureType.Ferenite -> {
                _mode.value = TempetureType.Celsius
            }
        }

        //this will refresh the values
        _currentWeather.value?.let {
            _currentWeather.value = it
        }
        _forecast.value?.let {
            _forecast.value = it
        }
    }

    fun refreshAll() {
        refreshCurrentWeather()
        refreshWeatherForecast()
    }
}