package com.ruslangrigoriev.topmovie.di

import android.app.Application
import com.ruslangrigoriev.topmovie.data.auth.ApiClient
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//@Module(includes = [NetworkModule::class, DataBaseModule::class ])
@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideRepository(): Repository {
        return RepositoryImpl(application)
    }

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient {
        return ApiClient(application)
    }

}