package com.ruslangrigoriev.topmovie.data.repository

import androidx.lifecycle.MutableLiveData
import com.ruslangrigoriev.topmovie.domain.dto.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.dto.profile.User
import com.ruslangrigoriev.topmovie.domain.dto.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.model.ResponseObject
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserData(): User?
    suspend fun getRatedMovies(accountID: Int): List<Media>?
    suspend fun getRatedTvShows(accountID: Int): List<Media>?
    suspend fun getFavoriteMovies(accountID: Int): List<Media>?
    suspend fun getFavoriteTvShows(accountID: Int): List<Media>?

    fun saveUserID(userID: Int)
    suspend fun markFavorite(mediaType: String, media_id: Int,media: Media?): ResponseObject?
    suspend fun checkIsFavorite(mediaID : Int) : Boolean
}