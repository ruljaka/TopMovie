package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import android.util.Log
import com.ruslangrigoriev.topmovie.data.local.FavoriteDAO
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.model.credits.CreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.model.video.VideoResponse
import com.ruslangrigoriev.topmovie.domain.utils.Result
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

//    private suspend fun <T> getResult(
//        request: suspend () -> Response<T>,
//        defaultErrorMessage: String
//    ): Result<T> {
//        return try {
//            val result = request.invoke()
//            if (result.isSuccessful) {
//                Result.success(result.body())
//            } else {
//                Result.error(result.errorBody().toString() ?: defaultErrorMessage)
//            }
//        } catch (e: Throwable) {
//            Result.error("No internet connection")
//        }
//    }

}