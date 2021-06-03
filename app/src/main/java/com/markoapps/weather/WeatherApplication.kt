package com.markoapps.weather

import android.app.Application
import com.markoapps.weather.di.AppModule
import com.markoapps.weather.di.ApplicationComponent
import com.markoapps.weather.di.DaggerApplicationComponent

class WeatherApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder().appModule(AppModule(this)).build()
    }
}