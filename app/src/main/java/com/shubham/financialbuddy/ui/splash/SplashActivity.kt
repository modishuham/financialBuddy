package com.shubham.financialbuddy.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.databinding.ActivitySplashBinding
import com.shubham.financialbuddy.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var handler: Handler
    private lateinit var animationHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())

        animationHandler = Handler(Looper.getMainLooper())
        animationHandler.postDelayed({
            val scaleUpAnim = AnimationUtils.loadAnimation(this, R.anim.anim_scale_up)
            scaleUpAnim.fillAfter = true
            scaleUpAnim.duration = 1000L
            binding.appLogo.startAnimation(scaleUpAnim)
            binding.h1.animate().alpha(1F).duration = 1000L
            binding.h2.animate().alpha(1F).duration = 1000L
        }, 1000L)

        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        animationHandler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}