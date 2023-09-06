package com.ruslangrigoriev.topmovie.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.topmovie.data.database.entity.UserDataEntity

@Dao
interface UserDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertUserData(userDataEntity: UserDataEntity)

    @Query("SELECT * FROM UserDataEntity WHERE isFavorite= 1")
     fun getFavoriteList(): List<UserDataEntity>

    @Query("SELECT * FROM UserDataEntity WHERE isRated= 1")
     fun getRatedList(): List<UserDataEntity>

    @Query("SELECT * FROM UserDataEntity WHERE id = :media_id")
     fun getEntity(media_id: Int) : UserDataEntity

    @Query("SELECT EXISTS (SELECT 1 FROM UserDataEntity WHERE id = :media_id)")
     fun exists(media_id: Int): Boolean

    @Query("UPDATE UserDataEntity SET isFavorite= 1 WHERE id = :media_id")
     fun markFavorite(media_id: Int)

    @Query("UPDATE UserDataEntity SET isFavorite= 0 WHERE id = :media_id")
     fun unmarkFavorite(media_id: Int)

    @Query("UPDATE UserDataEntity SET isRated= 1 WHERE id = :media_id")
     fun markRated(media_id: Int)

    @Query("DELETE FROM UserDataEntity WHERE id = :media_id")
     fun removeEntity(media_id: Int)

    @Query("DELETE FROM UserDataEntity")
     fun removeAll()

}