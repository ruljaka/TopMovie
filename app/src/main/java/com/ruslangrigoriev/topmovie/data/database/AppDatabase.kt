package com.ruslangrigoriev.topmovie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruslangrigoriev.topmovie.data.database.entity.UserDataEntity

@Database(entities = [UserDataEntity::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDataDAO(): UserDataDAO



}