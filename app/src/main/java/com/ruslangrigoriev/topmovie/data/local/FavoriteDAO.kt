package com.ruslangrigoriev.topmovie.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(media: Media)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteList(mediaList: List<Media>)

    @Query("DELETE FROM Media WHERE id = :media_id")
    suspend fun removeFavorite(media_id: Int)

    @Query("SELECT * FROM Media")
    suspend fun getFavoriteList(): List<Media>

}