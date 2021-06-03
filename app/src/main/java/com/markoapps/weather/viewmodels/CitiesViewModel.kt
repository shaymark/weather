package com.markoapps.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoapps.weather.convertors.TemperatureType

import com.markoapps.weather.models.WeatherModel
import com.markoapps.weather.networks.WeatherApi
import com.markoapps.weather.utils.Constans
import com.markoapps.weather.utils.LocationUtil
import kotlinx.coroutines.launch


class CitiesViewModel(val weaterApi: WeatherApi, val locationUtil: LocationUtil): ViewModel() {

    val selectedCityLiveData: MutableLiveData<WeatherModel> = MutableLiveData()

    val blockListIds: MutableSet<Long> = mutableSetOf()

    val _filterString: MutableLiveData<String> = MutableLiveData()
    val filterString: LiveData<String> = _filterString

    val _mode:MutableLiveData<TemperatureType> = MutableLiveData()
    val mode:LiveData<TemperatureType> = _mode

    val _citiesList:MutableLiveData<List<WeatherModel>> = MutableLiveData()
    val citiesList:LiveData<List<WeatherModel>> = _citiesList

    var filterLiveData: MutableLiveData<String> = MutableLiveData()

    var filteredList: MutableLiveData<List<WeatherModel>> = MutableLiveData()

    init {
        _filterString.value = ""
    }

    fun setFilter(filter: String) {
        _filterString.value = filter
        this.filterLiveData.value = filter
        filteredList.value =
             citiesList.value!!.filter {
                it.name.contains(filter, true) && !blockListIds.contains(it.id)
        }
    }

    fun refreshCities() {
        viewModelScope.launch {
            val location = locationUtil.getLocation()
            val result = if(location != null) {
                weaterApi.getCitiesByLocation(location!!.latitude.toString(), location!!.longitude.toString())
            } else {
                weaterApi.getCitiesByLocation(Constans.defaultLocation.lat.toString(), Constans.defaultLocation.lon.toString());
            }
            _citiesList.value = result.list
            setFilter("")
        }
    }

    fun setSelectedCity(id: Long) {
        selectedCityLiveData.value = _citiesList.value!!.find { it.id == id}
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
        refreshValues()
    }

    fun removeCity(id: Long) {
       blockListIds.add(id)
        refreshValues()
    }

    fun restoreAllCities() {
        blockListIds.clear()
        refreshValues()
    }

    fun refreshValues() {
        setFilter(filterLiveData.value!!)
    }

}