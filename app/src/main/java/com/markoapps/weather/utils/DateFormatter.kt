package com.markoapps.weather.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    var simpleDate: SimpleDateFormat = SimpleDateFormat("EEEE MMM dd, HH:MM")

    fun getTimeFromDate(date: Date): String {
        return simpleDate.format(date)
    }

    var simpleDateHourly: SimpleDateFormat = SimpleDateFormat("HH:MM")

    fun getTimeFromDateHourly(date: Date): String {
        return simpleDateHourly.format(date)
    }

}