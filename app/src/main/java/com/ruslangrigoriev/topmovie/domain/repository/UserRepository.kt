package com.ruslangrigoriev.topmovie.domain.repository

import com.ruslangrigoriev.topmovie.data.api.dto.profile.User
import com.ruslangrigoriev.topmovie.data.api.dto.StatusResponse
import com.ruslangrigoriev.topmovie.domain.model.Media

interface UserRepository {
    suspend fun getUserData(): User?
    suspend fun getRatedMovies(accountID: Int): List<Media>?
    suspend fun getRatedTvShows(accountID: Int): List<Media>?
    suspend fun getFavoriteMovies(accountID: Int): List<Media>?
    suspend fun getFavoriteTvShows(accountID: Int): List<Media>?

    fun saveUserID(userID: Int)
    suspend fun markFavorite(mediaType: String, mediaID: Int): StatusResponse?
    suspend fun markRated(mediaType: String, mediaID: Int, value: String): StatusResponse?
    suspend fun checkIsFavorite(mediaID: Int): Boolean
    suspend fun checkIsRated(mediaID: Int): Boolean

}