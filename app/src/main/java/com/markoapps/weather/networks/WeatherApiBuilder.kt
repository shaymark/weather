package com.markoapps.weather.networks

import com.markoapps.weather.utils.Constans
import me.sianaki.flowretrofitadapter.FlowCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class WeatherApiBuilder {
    fun getWeatherApi(): WeatherApi {
        val apiKey = Constans.APIKey

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response? {
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
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(WeatherApi::class.java)
    }
}