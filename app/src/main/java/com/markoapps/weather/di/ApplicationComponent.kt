package com.markoapps.weather.di

import com.markoapps.weather.views.CitiesFragment
import com.markoapps.weather.views.CityDetailsFragment
import com.markoapps.weather.views.HomeFragment
import dagger.Component


@Component(modules = [NetworkModule::class, AppModule::class])
interface ApplicationComponent {

    fun inject(view: HomeFragment)
    fun inject(view: CitiesFragment)
    fun inject(view: CityDetailsFragment)

}