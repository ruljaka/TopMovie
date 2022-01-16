package com.ruslangrigoriev.topmovie.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {
    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun removeFavorite(id: Int)

    @Query("SELECT * FROM favorites")
    fun getFavoriteList(): Flow<List<Favorite>>

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}