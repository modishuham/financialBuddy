package com.shubham.financialbuddy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val bottomNav = mBinding.bottomNavigation

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.calculatorFragment ||
                destination.id == R.id.seeAllFragment ||
                destination.id == R.id.rentReceiptListFragment ||
                destination.id == R.id.generateRentReceiptFragment ||
                destination.id == R.id.hraFragment
            ) {
                bottomNav.animate().translationY(bottomNav.height.toFloat()).duration = 300
            } else {
                bottomNav.animate().translationY(0F).duration = 300
            }
        }
        bottomNav.setupWithNavController(navController)
        hideBottomNavigationWhenActivityRecreated(navController)
    }

    private fun hideBottomNavigationWhenActivityRecreated(navController: NavController) {
        if (navController.currentDestination?.id == R.id.calculatorFragment ||
            navController.currentDestination?.id == R.id.seeAllFragment ||
            navController.currentDestination?.id == R.id.rentReceiptListFragment ||
            navController.currentDestination?.id == R.id.generateRentReceiptFragment ||
            navController.currentDestination?.id == R.id.hraFragment
        ) {
            mBinding.bottomNavigation.animate()
                .translationY(mBinding.bottomNavigation.height.toFloat()).duration = 300
        }
    }

}