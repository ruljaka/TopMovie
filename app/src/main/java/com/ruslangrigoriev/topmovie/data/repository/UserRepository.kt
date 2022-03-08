package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.dto.profile.User
import com.ruslangrigoriev.topmovie.domain.model.ResponseObject
import com.ruslangrigoriev.topmovie.domain.model.media.Media

interface UserRepository {
    suspend fun getUserData(): User?
    suspend fun getRatedMovies(accountID: Int): List<Media>?
    suspend fun getRatedTvShows(accountID: Int): List<Media>?
    suspend fun getFavoriteMovies(accountID: Int): List<Media>?
    suspend fun getFavoriteTvShows(accountID: Int): List<Media>?

    fun saveUserID(userID: Int)
    suspend fun markFavorite(mediaType: String, media_id: Int): ResponseObject?
    suspend fun markRated(mediaType: String, mediaID: Int, value: String): ResponseObject?
    suspend fun checkIsFavorite(mediaID: Int): Boolean
    suspend fun checkIsRated(mediaID: Int): Boolean

}