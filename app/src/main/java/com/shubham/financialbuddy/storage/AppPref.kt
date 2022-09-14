package com.shubham.financialbuddy.storage

import android.content.Context
import android.content.SharedPreferences
import com.shubham.financialbuddy.base.BaseApplication

object AppPref {

    private var sharedPreferences: SharedPreferences =
        BaseApplication.applicationContext().getSharedPreferences(
            "FinancialBuddyPreferences", Context.MODE_PRIVATE
        )

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBooleanDefaultTrue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }
}