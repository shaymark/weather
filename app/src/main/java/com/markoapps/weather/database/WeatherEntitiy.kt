package com.markoapps.weather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.markoapps.weather.models.WeatherModel

@Entity
data class WeatherEntitiy (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "object") val name: WeatherModel?
)

val gson = Gson()

class Converters {
    @TypeConverter
    fun gsonFromWeatherModel(value: WeatherModel?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun gsonToWeatherModel(jsonString: String?): WeatherModel? {
        return gson.fromJson(jsonString, WeatherModel::class.java)
    }
}