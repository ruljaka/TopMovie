package com.ruslangrigoriev.topmovie.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ruslangrigoriev.topmovie.domain.model.media.Media

@Dao
interface FavoriteDAO {
    @Insert
    suspend fun insertFavorite(media: Media)

    @Insert
    suspend fun insertFavoriteList(mediaList: List<Media>)

    @Query("DELETE FROM Media WHERE id = :media_id")
    suspend fun removeFavorite(media_id: Int)

    @Query("SELECT * FROM Media")
    fun getFavoriteList(): LiveData<List<Media>>
}