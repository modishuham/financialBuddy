package com.shubham.financialbuddy.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.shubham.financialbuddy.base.BaseApplication
import com.shubham.financialbuddy.utils.AppConstants
import com.shubham.financialbuddy.utils.Utils

object AppAnalytics {

    private val analytics: FirebaseAnalytics =
        FirebaseAnalytics.getInstance(BaseApplication.applicationContext())

    fun trackScreenLaunch(screenName: String) {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            bundle.putString(AppConstants.EVENT_PARAM_SCREEN_NAME, screenName)
            analytics.logEvent(AppConstants.EVENT_SCREEN_VIEW, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackCalculatorOpen(calculatorName: String) {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            bundle.putString(AppConstants.EVENT_PARAM_CALCULATOR_NAME, calculatorName)
            analytics.logEvent(AppConstants.EVENT_CALCULATOR_OPEN, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackCalculateHRA() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_CALCULATE_HRA, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackGenerateRent() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_GENERATE_RENT, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackInvestmentPlanView(planName: String) {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            bundle.putString(
                AppConstants.EVENT_PARAM_PLAN_NAME,
                planName
            )
            analytics.logEvent(AppConstants.EVENT_INVESTMENT_PLAN_VIEW, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackShareApp() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_SHARE_APP, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackRateUSClick() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_RATE, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackPolicyClick() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_POLICY, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackTermsClick() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_TERMS, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackCamMasterClick() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_CAM_MASTER, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackDarkMode() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_DARK_MODE, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackLightMode() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_LIGHT_MODE, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackAppOpen() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_APP_OPEN, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    fun trackSeeAllClick() {
        try {
            val bundle = Bundle()
            bundle.putString(
                AppConstants.EVENT_PARAM_DEVICE_ID,
                Utils.getDeviceID(BaseApplication.applicationContext())
            )
            analytics.logEvent(AppConstants.EVENT_SEE_ALL_CLICK, bundle)
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

}