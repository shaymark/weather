package com.markoapps.weather.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationUtil(val applicationContext: Context) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Location?  {
        val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        val location = client.lastLocation.awaitResult()
        val latLong = location?.run { "$latitude,$longitude" } ?: "null"
        Log.d(TAG, "getLocation: $latLong")
        return location
    }

    companion object {
        val TAG: String = LocationUtil::class.java.simpleName
    }

}

