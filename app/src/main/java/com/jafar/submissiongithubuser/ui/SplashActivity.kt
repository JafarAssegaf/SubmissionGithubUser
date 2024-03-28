package com.jafar.submissiongithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.jafar.submissiongithubuser.databinding.ActivitySplashBinding
import com.jafar.submissiongithubuser.ui.main.MainActivity
import com.jafar.submissiongithubuser.ui.main.MainViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel by viewModels<MainViewModel> {
            ViewModelFactory.getInstance(application)
        }

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isFinishing) {
                val intentToMain = Intent(this, MainActivity::class.java)
                startActivity(intentToMain)
                finish()
            }
        }, SPLASH_TIME_OUT)
    }

    companion object {
        const val SPLASH_TIME_OUT: Long = 4000
    }
}