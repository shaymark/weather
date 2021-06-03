package com.markoapps.weather.di

import android.content.Context
import com.markoapps.weather.utils.LocationUtil
import com.markoapps.weather.utils.TimeUtil
import dagger.Module
import dagger.Provides


@Module
class AppModule(val context: Context) {

    @Provides
    fun locationUtil(context: Context): LocationUtil {
        return LocationUtil(context)
    }

    @Provides
    fun timeUtil(): TimeUtil {
        return TimeUtil()
    }

    @Provides
    fun context(): Context {
        return context
    }

}