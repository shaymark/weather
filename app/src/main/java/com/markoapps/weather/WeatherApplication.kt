package com.markoapps.weather

import android.app.Application
import com.markoapps.weather.di.Providers

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Providers.initilized(this)
    }
}