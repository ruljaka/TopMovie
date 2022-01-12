package com.ruslangrigoriev.topmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        binding.bttmNav.itemIconTintList = null
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bttmNav, navHostFragment.navController)
    }
}