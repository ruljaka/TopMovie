package com.ruslangrigoriev.topmovie.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.topmovie.domain.model.UserDataEntity
import com.ruslangrigoriev.topmovie.domain.model.media.Media

@Dao
interface UserDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntity(entity: UserDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntityList(entityList: List<UserDataEntity>)

    @Query("SELECT * FROM UserDataEntity WHERE isFavorite= 1")
    suspend fun getFavoriteList(): List<UserDataEntity>

    @Query("SELECT * FROM UserDataEntity WHERE isRated= 1")
    suspend fun getRatedList(): List<UserDataEntity>

    @Query("SELECT * FROM UserDataEntity WHERE id = :media_id")
    suspend fun getEntity(media_id: Int) : UserDataEntity

    @Query("SELECT EXISTS (SELECT 1 FROM UserDataEntity WHERE id = :media_id)")
    suspend fun exists(media_id: Int): Boolean

    @Query("UPDATE UserDataEntity SET isFavorite= 1 WHERE id = :media_id")
    suspend fun markFavorite(media_id: Int)

    @Query("UPDATE UserDataEntity SET isFavorite= 0 WHERE id = :media_id")
    suspend fun unmarkFavorite(media_id: Int)

    @Query("UPDATE UserDataEntity SET isRated= 1 WHERE id = :media_id")
    suspend fun markRated(media_id: Int)

    /*@Query("UPDATE UserDataEntity SET isRated= 0 WHERE id = :media_id")
    suspend fun unmarkRated(media_id: Int)*/

    @Query("DELETE FROM UserDataEntity WHERE id = :media_id")
    suspend fun removeEntity(media_id: Int)

}