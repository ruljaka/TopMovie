package com.ruslangrigoriev.topmovie

import android.app.Application
import com.ruslangrigoriev.topmovie.di.*

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        //appComponent = DaggerAppComponent.create()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .dataBaseModule(DataBaseModule(this))
            .build()
    }
}