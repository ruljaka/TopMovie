package com.ruslangrigoriev.topmovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite

@Database(entities = [Favorite::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoriteDAO(): FavoriteDAO

    companion object{
        private var db_instance: AppDatabase? = null
        fun getAppDatabaseInstance(context: Context) : AppDatabase {
            if(db_instance == null) {
                db_instance = Room.databaseBuilder<AppDatabase>(
                     context.applicationContext, AppDatabase::class.java, "app_db"
                )
                .fallbackToDestructiveMigration()
                    .build();
            }
            return db_instance!!
        }
    }
}