package com.ruslangrigoriev.topmovie.data.repositoryImpl

import android.content.Context
import com.google.gson.JsonObject
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.api.dto.favorite.FavoriteCredentials
import com.ruslangrigoriev.topmovie.data.api.dto.favorite.FavoriteResponse
import com.ruslangrigoriev.topmovie.data.api.dto.profile.User
import com.ruslangrigoriev.topmovie.data.database.UserDataDAO
import com.ruslangrigoriev.topmovie.data.database.entity.UserDataEntity
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.repository.UserRepository
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.extensions.*
import java.util.*
import javax.inject.Inject


class UserRepoImpl
@Inject constructor(
    private val appContext: Context,
    private val apiService: ApiService,
    private val userDataDAO: UserDataDAO
) : UserRepository {

    override suspend fun getUserData(): User? {
        val session = appContext.getSessionID()
        return session?.let {
            apiService.getUser(session_id = it)
                .processResult()
        }
    }

    override suspend fun getRatedMovies(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listRatedMovies = session?.let {
            val listMovies = apiService.getRatedMovies(
                account_id = accountID,
                session_id = it
            )
                .processResult()?.movies
            listMovies.mapMovieToMedia()
        }
        saveRatedToDB(listRatedMovies)
        return listRatedMovies
    }

    override suspend fun getRatedTvShows(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listRatedTvShows = session?.let {
            val tvList = apiService.getRatedTvShow(
                account_id = accountID,
                session_id = it
            )
                .processResult()?.tvShows
            tvList.mapTvToMedia()
        }
        saveRatedToDB(listRatedTvShows)
        return listRatedTvShows
    }

    private suspend fun saveRatedToDB(listRated: List<Media>?) {
        listRated?.let { list ->
            val ratedIdsList = list.map { UserDataEntity(id = it.id, isRated = true) }
            for (entity in ratedIdsList) {
                if (userDataDAO.exists(entity.id)) {
                    userDataDAO.markRated(entity.id)
                } else {
                    userDataDAO.insertUserData(entity)
                }
            }
        }
    }

    override suspend fun getFavoriteMovies(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listFavoriteMovies = session?.let {
            val movieList = apiService.getFavoriteMovies(
                account_id = accountID,
                session_id = it
            )
                .processResult()?.movies
            movieList.mapMovieToMedia()
        }
        saveFavoriteToDB(listFavoriteMovies)
        return listFavoriteMovies
    }

    override suspend fun getFavoriteTvShows(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listFavoriteTvShows = session?.let {
            val tvShowList = apiService.getFavoriteTvShow(
                account_id = accountID,
                session_id = it
            )
                .processResult()?.tvShows
            tvShowList.mapTvToMedia()
        }
        saveFavoriteToDB(listFavoriteTvShows)
        return listFavoriteTvShows
    }

    private suspend fun saveFavoriteToDB(listFavorite: List<Media>?) {
        listFavorite?.let { list ->
            val favoriteIdsList = list.map { UserDataEntity(id = it.id, isFavorite = true) }
            for (entity in favoriteIdsList) {
                if (userDataDAO.exists(entity.id)) {
                    userDataDAO.markFavorite(entity.id)
                } else {
                    userDataDAO.insertUserData(entity)
                }
            }
        }
    }

    override suspend fun markRated(
        mediaType: String,
        mediaID: Int,
        value: String
    ): FavoriteResponse? {
        val session = appContext.getSessionID()
        val requestBody = JsonObject()
        requestBody.addProperty("value", value)
        val response = session?.let {
            if (mediaType == MOVIE_TYPE) {
                apiService.markAsRatedMovie(
                    movie_id = mediaID,
                    session_id = it,
                    body = requestBody
                ).processResult()
            } else {
                apiService.markAsRatedTvShow(
                    tv_id = mediaID,
                    session_id = it,
                    body = requestBody
                ).processResult()
            }
        }
        updateRatedDB(mediaID)
        return response
    }

    private suspend fun updateRatedDB(mediaID: Int) {
        if (userDataDAO.exists(mediaID)) {
            userDataDAO.markRated(mediaID)
        } else {
            userDataDAO.insertUserData(UserDataEntity(id = mediaID, isRated = true))
        }
    }

    override suspend fun markFavorite(
        mediaType: String, mediaID: Int
    ): FavoriteResponse? {
        val session = appContext.getSessionID()
        val userID = appContext.getUserID()
        val isFavorite = checkIsFavorite(mediaID)
        val favoriteCredentials = FavoriteCredentials(
            mediaType = mediaType.lowercase(Locale.getDefault()),
            mediaId = mediaID,
            favorite = !isFavorite
        )
        val response = session?.let {
            apiService.markAsFavorite(
                account_id = userID,
                session_id = it,
                favoriteCredentials = favoriteCredentials
            ).processResult()
        }
        updateFavoriteDB(isFavorite, mediaID)
        return response
    }

    private suspend fun updateFavoriteDB(isFavorite: Boolean, mediaID: Int) {
        when (isFavorite) {
            true -> userDataDAO.unmarkFavorite(mediaID)
            false -> {
                if (userDataDAO.exists(mediaID)) {
                    userDataDAO.markFavorite(mediaID)
                } else {
                    userDataDAO.insertUserData(UserDataEntity(id = mediaID, isFavorite = true))
                }
            }
        }
    }

    override fun saveUserID(userID: Int) {
        appContext.saveUserID(userID)
    }

    override suspend fun checkIsFavorite(mediaID: Int): Boolean {
        val listFavoriteIds = userDataDAO.getFavoriteList().map { it.id }
        return listFavoriteIds.contains(mediaID)
    }

    override suspend fun checkIsRated(mediaID: Int): Boolean {
        val listRatedIds = userDataDAO.getRatedList().map { it.id }
        return listRatedIds.contains(mediaID)
    }

}