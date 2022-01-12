package com.ruslangrigoriev.topmovie.presentation.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ruslangrigoriev.topmovie.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}