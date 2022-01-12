package com.ruslangrigoriev.topmovie.di

import android.app.Application
import android.content.Context
import com.ruslangrigoriev.topmovie.data.database.AppDatabase
import com.ruslangrigoriev.topmovie.data.database.FavoriteDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
@Module
class DataBaseModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteDAO {
        return appDatabase.getFavoriteDAO()
    }

    @Singleton
    @Provides
    fun provideAppContext() : Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDbInstance(): AppDatabase {
        return AppDatabase.getAppDatabaseInstance(provideAppContext())
    }
}