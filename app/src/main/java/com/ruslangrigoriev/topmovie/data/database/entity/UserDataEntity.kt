package com.ruslangrigoriev.topmovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "isRated")
    var isRated: Boolean = false
)