package com.markoapps.weather.utils

import java.util.*

class TimeUtil {

    fun getCurrentTime(): Date{
        return Calendar.getInstance().time
    }

    fun getTimeUntilMidnite(): Long {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 1)
        c[Calendar.HOUR_OF_DAY] = 0
        c[Calendar.MINUTE] = 0
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        val howMany = c.timeInMillis - System.currentTimeMillis()
        return howMany
    }

}