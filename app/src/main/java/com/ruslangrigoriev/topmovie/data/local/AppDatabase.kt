package com.ruslangrigoriev.topmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruslangrigoriev.topmovie.domain.model.UserDataEntity
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.GenreListConverter

@Database(entities = [UserDataEntity::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    //abstract fun getFavoriteDAO(): FavoriteDAO

    abstract fun getUserDataDAO(): UserDataDAO



}