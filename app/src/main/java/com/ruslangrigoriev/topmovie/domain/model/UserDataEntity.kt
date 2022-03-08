package com.ruslangrigoriev.topmovie.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "isRated")
    val isRated: Boolean = false
)