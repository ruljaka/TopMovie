package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import com.ruslangrigoriev.topmovie.data.local.FavoriteDAO
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.model.auth.Session
import com.ruslangrigoriev.topmovie.domain.model.profile.User
import com.ruslangrigoriev.topmovie.domain.model.credits.CreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.model.video.VideoResponse
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.getSession
import javax.inject.Inject

class RepositoryImpl(private val application: Application) : Repository {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var favoriteDAO: FavoriteDAO

    private var session: Session? = null

    init {
        application.appComponent.inject(this)
    }

    override suspend fun getMoviesTrending(page: Int): MovieResponse? {
        val response = apiService.getTrending(page = page)
        return getResultOrError(response)
    }

    override suspend fun getMoviesNow(): MovieResponse? {
        val response = apiService.getMoviesNow()
        return getResultOrError(response)
    }

    override suspend fun getMoviesPopular(): MovieResponse? {
        val response = apiService.getMoviesPopular()
        return getResultOrError(response)
    }

    override suspend fun getMovieDetails(id: Int): Movie? {
        val response = apiService.getMovieDetails(id)
        return getResultOrError(response)
    }

    override suspend fun getMovieCredits(id: Int): CreditsResponse? {
        val response = apiService.getMovieCredits(id)
        return getResultOrError(response)
    }

    override suspend fun getSearchMoviesPagedResult(query: String, page: Int): MovieResponse? {
        val response = apiService.searchPagedMovie(query = query, page = page)
        return getResultOrError(response)
    }

    override suspend fun getTvNow(): TvResponse? {
        val response = apiService.getTVNow()
        return getResultOrError(response)
    }

    override suspend fun getTvPopular(): TvResponse? {
        val response = apiService.getTvPopular()
        return getResultOrError(response)
    }

    override suspend fun getTvDetails(id: Int): TvShow? {
        val response = apiService.getTvDetails(id)
        return getResultOrError(response)
    }

    override suspend fun getTvCredits(id: Int): CreditsResponse? {
        val response = apiService.getTvCredits(id)
        return getResultOrError(response)
    }

    override suspend fun getSearchTvPagedResult(query: String, page: Int): TvResponse? {
        val response = apiService.searchPagedTvShow(query = query, page = page)
        return getResultOrError(response)
    }

    override suspend fun getPerson(person_id: Int): Person? {
        val response = apiService.getPerson(person_id)
        return getResultOrError(response)
    }

    override suspend fun getPersonCredits(person_id: Int): PersonCreditsResponse? {
        val response = apiService.getPersonCredits(person_id)
        return getResultOrError(response)
    }

    override suspend fun getMovieVideo(movie_id: Int): VideoResponse? {
        val response = apiService.getMovieVideos(movie_id)
        return getResultOrError(response)
    }

    override suspend fun getTvVideo(movie_id: Int): VideoResponse? {
        val response = apiService.getTvVideos(movie_id)
        return getResultOrError(response)
    }

    override suspend fun getUserData(): User? {
        session = application.applicationContext.getSession()
        session?.let {
            return getResultOrError(
                apiService.getUser(
                    session_id = it.sessionId
                )
            )
        }
        return null
    }

    override suspend fun getRatedMovies(accountID: Int): MovieResponse? {
        session?.let {
            return getResultOrError(
                apiService.getRatedMovies(
                    account_id = accountID,
                    session_id = session!!.sessionId
                )
            )
        }
        return null
    }

    override suspend fun getRatedTvShows(accountID: Int): TvResponse? {
        session?.let {
            return getResultOrError(
                apiService.getRatedTvShow(
                    account_id = accountID,
                    session_id = session!!.sessionId
                )
            )
        }
        return null
    }

    override suspend fun getFavoriteMovies(accountID: Int): MovieResponse? {
        session?.let {
            return getResultOrError(
                apiService.getFavoriteMovies(
                    account_id = accountID,
                    session_id = session!!.sessionId
                )
            )
        }
        return null
    }

    override suspend fun getFavoriteTvShows(accountID: Int): TvResponse? {
        session?.let {
            return getResultOrError(
                apiService.getFavoriteTvShow(
                    account_id = accountID,
                    session_id = session!!.sessionId
                )
            )
        }
        return null
    }

//    override fun getFavoriteList(): Flow<List<Favorite>> {
//
//        return favoriteDAO.getFavoriteList()
//    }
//
//    override suspend fun insertFavorite(favorite: Favorite) {
//        favoriteDAO.insertFavorite(favorite)
//        Log.d(TAG, "RepositoryImpl -> insertFavorite")
//    }
//
//    override suspend fun removeFavorite(id: Int) {
//        Log.d(TAG, "RepositoryImpl -> removeFavorite")
//        favoriteDAO.removeFavorite(id)
//    }
//
//    override suspend fun deleteFavorite(favorite: Favorite) {
//        favoriteDAO.deleteFavorite(favorite)
//        Log.d(TAG, "RepositoryImpl -> deleteFavorite")
//    }


}