package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import android.util.Log
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.database.FavoriteDAO
import com.ruslangrigoriev.topmovie.domain.model.credits.Credits
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.ResultMovies
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import com.ruslangrigoriev.topmovie.domain.model.tv.ResultTv
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl(private val application: Application) : Repository {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var favoriteDAO: FavoriteDAO

    init {
        application.appComponent.inject(this)
    }

    override suspend fun getMoviesTrending(page: Int): ResultMovies? {
        val response = apiService.getTrending(page = page)
        return getResultOrError(response)
    }

    override suspend fun getMoviesNow(): ResultMovies? {
        val response = apiService.getMoviesNow()
        return getResultOrError(response)
    }

    override suspend fun getMoviesPopular(): ResultMovies? {
        val response = apiService.getMoviesPopular()
        return getResultOrError(response)
    }

    override suspend fun getMovieDetails(id: Int): Movie? {
        val response = apiService.getMovieDetails(id)
        return getResultOrError(response)
    }

    override suspend fun getMovieCredits(id: Int): Credits? {
        val response = apiService.getMovieCredits(id)
        return getResultOrError(response)
    }

    override suspend fun getSearchMoviesPagedResult(query: String, page: Int): ResultMovies? {
        val response = apiService.searchPagedMovie(query = query, page = page)
        return getResultOrError(response)
    }

    override suspend fun getTvNow(): ResultTv? {
        val response = apiService.getTVNow()
        return getResultOrError(response)
    }

    override suspend fun getTvPopular(): ResultTv? {
        val response = apiService.getTvPopular()
        return getResultOrError(response)
    }

    override suspend fun getTvDetails(id: Int): TvShow? {
        val response = apiService.getTvDetails(id)
        return getResultOrError(response)
    }

    override suspend fun getTvCredits(id: Int): Credits? {
        val response = apiService.getTvCredits(id)
        return getResultOrError(response)
    }

    override suspend fun getSearchTvPagedResult(query: String, page: Int): ResultTv? {
        val response = apiService.searchPagedTvShow(query = query, page = page)
        return getResultOrError(response)
    }

    override suspend fun getPerson(person_id: Int): Person? {
        val response = apiService.getPerson(person_id)
        return getResultOrError(response)
    }

    override suspend fun getPersonCredits(person_id: Int): PersonCredits? {
        val response = apiService.getPersonCredits(person_id)
        return getResultOrError(response)
    }

    override fun getFavoriteList(): Flow<List<Favorite>> {
        Log.d(TAG, "RepositoryImpl -> getFavoriteList")
        return favoriteDAO.getFavoriteList()
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        favoriteDAO.insertFavorite(favorite)
        Log.d(TAG, "RepositoryImpl -> insertFavorite")
    }

    override suspend fun removeFavorite(id: Int) {
        Log.d(TAG, "RepositoryImpl -> removeFavorite")
        favoriteDAO.removeFavorite(id)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDAO.deleteFavorite(favorite)
        Log.d(TAG, "RepositoryImpl -> deleteFavorite")
    }

    private fun <T : Any> getResultOrError(response: Response<T>): T? {
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Throwable(response.errorBody().toString())
        }
    }

}