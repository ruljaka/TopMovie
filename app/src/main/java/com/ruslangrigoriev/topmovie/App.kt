package com.ruslangrigoriev.topmovie

import android.app.Application
import com.ruslangrigoriev.topmovie.domain.utils.extensions.getColorMode
import com.ruslangrigoriev.topmovie.domain.utils.extensions.changeColorMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        changeColorMode(getColorMode())
        Timber.plant(Timber.DebugTree())
    }
}