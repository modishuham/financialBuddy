package com.shubham.financialbuddy.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import com.shubham.financialbuddy.model.RentReceiptData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object Utils {

    fun rupeeFormat(value: String): String {
        var value = value
        value = value.replace(",", "")
        val lastDigit = value[value.length - 1]
        var result = ""
        val len = value.length - 1
        var nDigits = 0
        for (i in len - 1 downTo 0) {
            result = value[i].toString() + result
            nDigits++
            if (nDigits % 2 == 0 && i > 0) {
                result = ",$result"
            }
        }
        return result + lastDigit
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun getMonthsBetweenDates(startDate: String, endDate: String): ArrayList<String> {
        val formater: DateFormat = SimpleDateFormat("MMM-yyyy")
        val beginCalendar: Calendar = Calendar.getInstance()
        val finishCalendar: Calendar = Calendar.getInstance()
        try {
            beginCalendar.time = formater.parse(startDate)
            finishCalendar.time = formater.parse(endDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val monthList = ArrayList<String>()
        while (beginCalendar.before(finishCalendar)) {
            // add one month to date per loop
            val date: String = formater.format(beginCalendar.time)
            Log.e("____________", date)
            monthList.add(date)
            beginCalendar.add(Calendar.MONTH, 1)
        }
        monthList.add(formater.format(beginCalendar.time))
        return monthList
    }

    fun getQuarterlyMonth(monthList: ArrayList<String>): ArrayList<RentReceiptData> {
        var startingMonth = monthList[0]
        val quarterlyMonthList = ArrayList<RentReceiptData>()
        val remainingMonthsCount = monthList.size % 3
        for (i in 0 until monthList.size) {
            if ((i + 1) % 3 == 0) {
                val data = RentReceiptData(false, startingMonth + "_" + monthList[i])
                quarterlyMonthList.add(data)
                if ((i + 1) <= monthList.size - 1) {
                    startingMonth = monthList[i + 1]
                }
            }
        }
        if (remainingMonthsCount > 0) {
            val startIndex = monthList.size - remainingMonthsCount
            for (i in startIndex until monthList.size) {
                val data = RentReceiptData(true, monthList[i])
                quarterlyMonthList.add(data)
            }
        }
        return quarterlyMonthList
    }

    fun getMonthlyData(monthList: ArrayList<String>): ArrayList<RentReceiptData> {
        val monthlyList = ArrayList<RentReceiptData>()
        for (i in 0 until monthList.size) {
            val data = RentReceiptData(true, monthList[i])
            monthlyList.add(data)
        }
        return monthlyList
    }

    fun convertDateToMonthFormat(date: String): String {
        val splitList = date.split(" ")
        return splitList[1] + "-" + splitList[2]
    }
}