package com.ruslangrigoriev.topmovie.data.repository

import android.content.Context
import com.ruslangrigoriev.topmovie.data.local.FavoriteDAO
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.dto.profile.User
import com.ruslangrigoriev.topmovie.domain.model.FavoriteCredentials
import com.ruslangrigoriev.topmovie.domain.model.ResponseObject
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.*
import javax.inject.Inject

class UserRepoImpl
@Inject constructor(
    private val appContext: Context,
    private val apiService: ApiService,
    private val favoriteDAO: FavoriteDAO
) : UserRepository {

    override suspend fun getUserData(): User? {
        val session = appContext.getSessionID()
        return session?.let {
            getResultOrError(
                apiService.getUser(
                    session_id = it
                )
            )
        }
    }

    override suspend fun getRatedMovies(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listRatedMovies =  session?.let {
            val listMovies = getResultOrError(
                apiService.getRatedMovies(
                    account_id = accountID,
                    session_id = it
                )
            )?.movies
            mapMovieToMedia(listMovies)
        }
        //TODO save to DB
        return listRatedMovies
    }

    override suspend fun getRatedTvShows(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listRatedTvShows =  session?.let {
             val tvList = getResultOrError(
                apiService.getRatedTvShow(
                    account_id = accountID,
                    session_id = it
                )
            )?.tvShows
            mapTvShowToMedia(tvList)
        }
        //TODO save to DB
        return listRatedTvShows
    }

    override suspend fun getFavoriteMovies(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listFavoriteMovies = session?.let {
            val movieList = getResultOrError(
                apiService.getFavoriteMovies(
                    account_id = accountID,
                    session_id = it
                )
            )?.movies
            mapMovieToMedia(movieList)
        }
        listFavoriteMovies?.let {
            favoriteDAO.insertFavoriteList(listFavoriteMovies)
        }
        return listFavoriteMovies
    }

//    override suspend fun getFavoriteList(coroutineScope: CoroutineScope): List<Media>? {
//        val user = getUserData()
//        user?.let { user ->
//            saveUserID(user.id)
//            coroutineScope.async {
//                getFavoriteMovies(user.id)?.let { favoriteDAO.insertFavoriteList(it) }
//            }
//             coroutineScope.async {
//               getFavoriteTvShows(user.id)?.let { favoriteDAO.insertFavoriteList(it) }
//            }
//
//
//            return favoriteDAO.getFavoriteList()
//    }

    override suspend fun getFavoriteTvShows(accountID: Int): List<Media>? {
        val session = appContext.getSessionID()
        val listFavoriteTvShows = session?.let {
            val tvShowList = getResultOrError(
                apiService.getFavoriteTvShow(
                    account_id = accountID,
                    session_id = it
                )
            )?.tvShows
            mapTvShowToMedia(tvShowList)
        }
        listFavoriteTvShows?.let {
            favoriteDAO.insertFavoriteList(listFavoriteTvShows)
        }
        return listFavoriteTvShows
    }

    override suspend fun markFavorite(
        mediaType: String, media_id: Int,media: Media?
    ): ResponseObject? {
        val session = appContext.getSessionID()
        val userID = appContext.getUserID()
        val favoriteCredentials = FavoriteCredentials(
            mediaType = mediaType,
            mediaId = media_id,
            favorite = true
        )
        val response = session?.let {
            getResultOrError(
                apiService.markAsFavorite(
                    account_id = userID,
                    session_id = it,
                    favoriteCredentials = favoriteCredentials
                )
            )
        }
        if (media != null) {
            favoriteDAO.insertFavorite(media)
        }
        return response
    }

    override fun saveUserID(userID: Int) {
        appContext.saveUserID(userID)
    }

}