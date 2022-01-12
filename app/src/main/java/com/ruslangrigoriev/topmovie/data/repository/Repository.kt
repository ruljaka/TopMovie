package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.domain.model.credits.Credits
import com.ruslangrigoriev.topmovie.domain.model.details.Details
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.Result
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {
    suspend fun getPagedTrending(page: Int): Result?
    suspend fun getDetails(id: Int): Details?
    suspend fun getCast(id: Int): Credits?
    suspend fun getSearchPagedResult(query: String, page: Int): Result?
    suspend fun getPerson(person_id: Int): Person?
    suspend fun getPersonCredits(person_id: Int): PersonCredits?

    fun getFavoriteList(): Flow<List<Favorite>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun removeFavorite(id: Int)
    suspend fun deleteFavorite(favorite: Favorite)
}