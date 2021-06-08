package com.markoapps.weather.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.markoapps.weather.convertors.TemperatureType
import com.markoapps.weather.models.ForecastModel
import com.markoapps.weather.models.WeatherModel
import com.markoapps.weather.networks.WeatherApi
import com.markoapps.weather.repositories.WeatherRepository
import com.markoapps.weather.utils.Constans
import com.markoapps.weather.utils.LocationUtil
import com.markoapps.weather.utils.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
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

class CurrentWeatherViewModel(val weatherRepository: WeatherRepository, val weatherApi: WeatherApi, val locationUtil: LocationUtil, val timeUtil: TimeUtil) : ViewModel() {

    companion object {
        val TAG: String = CurrentWeatherViewModel::class.java.simpleName
    }

    private val _mode: MutableLiveData<TemperatureType> = MutableLiveData()
    val mode: LiveData<TemperatureType> = _mode

    private val _currentTime: MutableLiveData<Date> = MutableLiveData()
    val currentTime: LiveData<Date> = _currentTime

    private val _currentWeather: MutableLiveData<WeatherModel> = MutableLiveData()
    val currentWeather: LiveData<WeatherModel> = _currentWeather

    private val _forecast: MutableLiveData<List<ForecastModel>> = MutableLiveData()
    val forecast: LiveData<List<ForecastModel>> = _forecast

    val currentWeatherFlow = MutableSharedFlow<WeatherModel>()
    val currentWeatherLiveData = currentWeatherFlow.asLiveData()

    init {
        _mode.value = TemperatureType.Celsius
    }

    fun refreshCurrentWeather(){



        viewModelScope.launch {
            val location = locationUtil.getLocation()

            val weatherModel = weatherApi.getCurrentWeatherByCityFlow("rehovot")
            weatherModel.collect{
                currentWeatherFlow.emit(it)
            }

            _currentTime.value = timeUtil.getCurrentTime()

//            try {
//                val response = if(location != null) {
//                    weatherApi.getCurrentWeatherByLocation(location.latitude.toString(), location.longitude.toString() )
//                } else {
//                    weatherApi.getCurrentWeatherByCity(Constans.defaultCityName)
//                }
//
//                _currentTime.value = timeUtil.getCurrentTime()
//                _currentWeather.value = response
//
//            } catch (e: Exception) {
//                Log.d(TAG, "refreshCurrentWeather exception: ${e.message}")
//            }
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
            TemperatureType.Celsius -> {
                _mode.value = TemperatureType.Fahrenheit
            }
            TemperatureType.Fahrenheit -> {
                _mode.value = TemperatureType.Celsius
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