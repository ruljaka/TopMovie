package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import android.util.Log
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.database.FavoriteDAO
import com.ruslangrigoriev.topmovie.domain.model.credits.Credits
import com.ruslangrigoriev.topmovie.domain.model.details.Details
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.model.movies.Result
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
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

    override suspend fun getPagedTrending(page: Int): Result? {
        val response = apiService.getPagedTrending(page = page)
        return getResultOrError(response)
    }

    override suspend fun getDetails(id: Int): Details? {
        val response = apiService.getDetails(id)
        return getResultOrError(response)
    }

    override suspend fun getCast(id: Int): Credits? {
        val response = apiService.getCast(id)
        return getResultOrError(response)
    }

    override suspend fun getSearchPagedResult(query: String, page: Int): Result? {
        val response = apiService.searchPagedMovie(query = query, page = page)
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