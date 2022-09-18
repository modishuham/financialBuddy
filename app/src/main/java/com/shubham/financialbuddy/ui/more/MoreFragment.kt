package com.shubham.financialbuddy.ui.more

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdRequest
import com.shubham.financialbuddy.BuildConfig
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.analytics.AppAnalytics
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentMoreBinding
import com.shubham.financialbuddy.storage.AppPref
import com.shubham.financialbuddy.storage.SharedPrefConstants


class MoreFragment : BaseFragment() {

    private lateinit var mBinding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMoreBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppAnalytics.trackScreenLaunch("more_screen")

        val adRequest: AdRequest = AdRequest.Builder().build()
        mBinding.adViewMore.loadAd(adRequest)

        mBinding.tvAppVersion.text = BuildConfig.VERSION_NAME

        val isDarkModeEnabled = AppPref.getBooleanDefaultTrue(SharedPrefConstants.DARK_MODE_ENABLED)
        if (isDarkModeEnabled) {
            mBinding.radioButtonDark.isChecked = true
        } else {
            mBinding.radioButtonLight.isChecked = true
        }

        mBinding.rgTheme.setOnCheckedChangeListener { radioGroup, i ->
            val checkedRadioButton = radioGroup.findViewById<RadioButton>(i)
            if (checkedRadioButton.text.equals("Dark")) {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_YES
                    )
                AppPref.putBoolean(SharedPrefConstants.DARK_MODE_ENABLED, true)
                AppAnalytics.trackDarkMode()
            } else {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_NO
                    )
                AppPref.putBoolean(SharedPrefConstants.DARK_MODE_ENABLED, false)
                AppAnalytics.trackLightMode()
            }
        }

        mBinding.tvShareApp.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey download and share FinancialBuddy. Check out FinancialBuddy at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
            AppAnalytics.trackShareApp()
        }

        mBinding.tvRateUs.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)
                    )
                )
                AppAnalytics.trackRateUSClick()
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                    )
                )
            }
        }

        mBinding.tvPrivacyPolicy.setOnClickListener {
            AppAnalytics.trackPolicyClick()
            try {
                val policyUrl = "https://financialbuddy.000webhostapp.com/policy.html"
                val uri = Uri.parse(policyUrl)
                val intentBuilder = CustomTabsIntent.Builder()
                val params = CustomTabColorSchemeParams.Builder()
                    .setNavigationBarColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.appBackgroundColor
                        )
                    )
                    .setToolbarColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.appBackgroundColor
                        )
                    )
                    .setSecondaryToolbarColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.appBackgroundColor
                        )
                    )
                    .build()
                intentBuilder.setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params)
                val customTabsIntent = intentBuilder.build()
                customTabsIntent.launchUrl(requireActivity(), uri)
            } catch (ex: Exception) {

            }
        }

        mBinding.tvTerms.setOnClickListener {
            AppAnalytics.trackTermsClick()
            try {
                val termsUrl = "https://financialbuddy.000webhostapp.com/terms.html"
                val uri = Uri.parse(termsUrl)
                val intentBuilder = CustomTabsIntent.Builder()
                val params = CustomTabColorSchemeParams.Builder()
                    .setNavigationBarColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.appBackgroundColor
                        )
                    )
                    .setToolbarColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.appBackgroundColor
                        )
                    )
                    .setSecondaryToolbarColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.appBackgroundColor
                        )
                    )
                    .build()
                intentBuilder.setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params)
                val customTabsIntent = intentBuilder.build()
                customTabsIntent.launchUrl(requireActivity(), uri)
            } catch (ex: Exception) {

            }
        }

        mBinding.tvCamMaster.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.m.cammstrind")
                    )
                )
                AppAnalytics.trackCamMasterClick()
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.m.cammstrind")
                    )
                )
            }
        }
    }
}