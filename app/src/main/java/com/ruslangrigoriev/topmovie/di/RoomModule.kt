package com.ruslangrigoriev.topmovie.di

import android.content.Context
import androidx.room.Room
import com.ruslangrigoriev.topmovie.data.local.AppDatabase
import com.ruslangrigoriev.topmovie.data.local.UserDataDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .build();
    }

    @Singleton
    @Provides
    fun provideFavoriteDAO(appDatabase: AppDatabase): UserDataDAO {
        return appDatabase.getUserDataDAO()
    }
}