package com.ruslangrigoriev.topmovie

import android.app.Application
import com.ruslangrigoriev.topmovie.dependencies.AppComponent
import com.ruslangrigoriev.topmovie.dependencies.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()

    }
}