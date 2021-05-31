package com.markoapps.weather.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weatherentitiy")
    suspend fun getAllWeather(): List<WeatherEntitiy>

    @Insert
    fun insertAll(vararg weatherEntitiys: WeatherEntitiy)

    @Delete
    fun delete(weatherEntitiys: WeatherEntitiy)

    @Query("DELETE FROM WeatherEntitiy")
    fun deleteAll()
}