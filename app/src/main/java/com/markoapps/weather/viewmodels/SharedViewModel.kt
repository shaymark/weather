package com.markoapps.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> = _title

    private val _buttons: MutableLiveData<ValueWrapper<Int>> = MutableLiveData()
    val buttons: LiveData<ValueWrapper<Int>> = _buttons

    private val _menuId: MutableLiveData<Int> = MutableLiveData()
    val menuId: LiveData<Int> = _menuId

    fun onButtonPressed(buttonId: Int){
        _buttons.value = ValueWrapper(buttonId)
    }

    fun setMenu(menuId: Int) {
        _menuId.value = menuId
    }

    fun setTitle(title: String){
        _title.value = title
    }

}