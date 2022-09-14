package com.shubham.financialbuddy.base

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    var mAppContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        mAppContext = applicationContext
    }

    fun getAppContext(): Context? {
        return mAppContext
    }
}