package com.shubham.financialbuddy.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.shubham.financialbuddy.storage.AppPref
import com.shubham.financialbuddy.storage.SharedPrefConstants

class BaseApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        checkDarkMode()
    }

    private fun checkDarkMode() {
        val isDarkModeEnabled = AppPref.getBooleanDefaultTrue(SharedPrefConstants.DARK_MODE_ENABLED)
        if (isDarkModeEnabled) {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_YES
                )
        } else {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_NO
                )
        }
    }

}