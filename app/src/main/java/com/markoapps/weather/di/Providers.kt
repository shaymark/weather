package com.markoapps.weather.di

import android.content.Context
import com.markoapps.weather.networks.WeatherApi
import com.markoapps.weather.utils.Constans
import com.markoapps.weather.utils.LocationUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object Providers {

    fun initilized(applicationContext: Context) {
        appContext = applicationContext
    }

    lateinit var appContext: Context

    val retrofit: WeatherApi by lazy {

        val apiKey = Constans.APIKey

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response? {
                    // Request customization: add request headers
                    val request = chain.request().newBuilder()
                        .url(chain.request().url().newBuilder()
                            .addQueryParameter("appid", apiKey)
                            .build()).build()

                    return chain.proceed(request)
                }
            })
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        retrofit.create(WeatherApi::class.java)
    }

    val locationUtil: LocationUtil by lazy {
        LocationUtil(appContext)
    }


}