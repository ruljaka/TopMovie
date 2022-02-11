package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import com.ruslangrigoriev.topmovie.data.local.FavoriteDAO
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.model.FavoriteCredentials
import com.ruslangrigoriev.topmovie.domain.model.ResponseObject
import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.model.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.profile.User
import com.ruslangrigoriev.topmovie.domain.model.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.model.video.Video
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.mappers.MovieMapper
import com.ruslangrigoriev.topmovie.domain.utils.mappers.TvMapper
import javax.inject.Inject

class RepositoryImpl(private val application: Application) : Repository {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var favoriteDAO: FavoriteDAO

    init {
        application.appComponent.inject(this)
    }

    override suspend fun getMoviesTrending(page: Int): List<Media> {
        val response = apiService.getTrending(page = page)
        val trendingList =  getResultOrError(response)?.movies
        return mapMovieToMedia(trendingList)
    }

    override suspend fun getMoviesNow(): List<Media> {
        val response = apiService.getMoviesNow()
        val nowList = getResultOrError(response)?.movies
        return mapMovieToMedia(nowList)
    }

    override suspend fun getMoviesPopular(): List<Media> {
        val response = apiService.getMoviesPopular()
        val popularList = getResultOrError(response)?.movies
        return mapMovieToMedia(popularList)
    }

    override suspend fun getMovieDetails(id: Int): Media? {
        val response = apiService.getMovieDetails(id)
        getResultOrError(response)?.let {
            return MovieMapper.map(it)
        }
        return null
    }

    override suspend fun getMovieCredits(id: Int): List<Cast> {
        val response = apiService.getMovieCredits(id)
        return getResultOrError(response)?.cast ?: emptyList()
    }

    override suspend fun searchMoviesPaged(query: String, page: Int): List<Media> {
        val response = apiService.searchPagedMovie(query = query, page = page)
        val searchList = getResultOrError(response)?.movies
        return mapMovieToMedia(searchList)
    }

    override suspend fun getTvNow(): List<Media> {
        val response = apiService.getTVNow()
        val nowList = getResultOrError(response)?.tvShows
        return mapTvShowToMedia(nowList)
    }

    override suspend fun getTvPopular(): List<Media> {
        val response = apiService.getTvPopular()
        val popularList = getResultOrError(response)?.tvShows
        return mapTvShowToMedia(popularList)
    }

    override suspend fun getTvDetails(id: Int): Media? {
        val response = apiService.getTvDetails(id)
        getResultOrError(response)?.let {
            return TvMapper.map(it)
        }
        return null
    }

    override suspend fun getTvCredits(id: Int): List<Cast> {
        val response = apiService.getTvCredits(id)
        return getResultOrError(response)?.cast ?: emptyList()
    }

    override suspend fun searchTvPaged(query: String, page: Int): List<Media> {
        val response = apiService.searchPagedTvShow(query = query, page = page)
        val searchList = getResultOrError(response)?.tvShows
        return mapTvShowToMedia(searchList)
    }

    override suspend fun getPerson(person_id: Int): Person? {
        val response = apiService.getPerson(person_id)
        return getResultOrError(response)
    }

    override suspend fun getPersonCredits(person_id: Int): List<Media> {
        val response = apiService.getPersonCredits(person_id)
        val movieCastList = getResultOrError(response)?.cast
        return mapMovieToMedia(movieCastList)
    }

    override suspend fun getMovieVideo(movie_id: Int): List<Video> {
        val response = apiService.getMovieVideos(movie_id)
        return getResultOrError(response)?.videos ?: emptyList()
    }

    override suspend fun getTvVideo(tv_id: Int): List<Video> {
        val response = apiService.getTvVideos(tv_id)
        return getResultOrError(response)?.videos ?: emptyList()
    }

    override suspend fun getUserData(): User? {
        val session = application.applicationContext.getSessionID()
        session?.let {
            return getResultOrError(
                apiService.getUser(
                    session_id = it
                )
            )
        }
        return null
    }

    override suspend fun getRatedMovies(accountID: Int): MovieResponse? {
        val session = application.applicationContext.getSessionID()
        session?.let {
            return getResultOrError(
                apiService.getRatedMovies(
                    account_id = accountID,
                    session_id = it
                )
            )
        }
        return null
    }

    override suspend fun getRatedTvShows(accountID: Int): TvResponse? {
        val session = application.applicationContext.getSessionID()
        session?.let {
            return getResultOrError(
                apiService.getRatedTvShow(
                    account_id = accountID,
                    session_id = it
                )
            )
        }
        return null
    }

    override suspend fun getFavoriteMovies(accountID: Int): List<Media>? {
        val session = application.applicationContext.getSessionID()
        return session?.let {
            val movieList =  getResultOrError(
                apiService.getFavoriteMovies(
                    account_id = accountID,
                    session_id = it
                )
            )?.movies
             mapMovieToMedia(movieList)
        }
    }

    override suspend fun getFavoriteTvShows(accountID: Int): List<Media>? {
        val session = application.applicationContext.getSessionID()
        return session?.let {
            val tvShowList = getResultOrError(
                apiService.getFavoriteTvShow(
                    account_id = accountID,
                    session_id = it
                )
            )?.tvShows
            mapTvShowToMedia(tvShowList)
        }
    }

    override suspend fun markFavorite(
        favoriteCredentials: FavoriteCredentials
    ): ResponseObject? {
        val session = application.applicationContext.getSessionID()
        val userID = application.applicationContext.getUserID()
        session?.let {
            return getResultOrError(
                apiService.markAsFavorite(
                    account_id = userID,
                    session_id = it,
                    favoriteCredentials = favoriteCredentials
                )
            )
        }
        return null
    }

    override fun saveUserID(userID: Int) {
        application.applicationContext.saveUserID(userID)
    }


}