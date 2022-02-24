package com.ruslangrigoriev.topmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.GenreListConverter

@Database(entities = [Media::class], version = 1, exportSchema = false)
@TypeConverters(GenreListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoriteDAO(): FavoriteDAO

}