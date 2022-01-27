package com.ruslangrigoriev.topmovie

import android.app.Application
import com.ruslangrigoriev.topmovie.di.*
import timber.log.Timber


class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        //appComponent = DaggerAppComponent.create()
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .dataBaseModule(DataBaseModule(this))
            .build()
    }
}