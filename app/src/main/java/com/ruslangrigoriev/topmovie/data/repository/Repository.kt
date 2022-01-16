package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.model.credits.Credits
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.ResultMovies
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import com.ruslangrigoriev.topmovie.domain.model.tv.ResultTv
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMoviesTrending(page: Int): ResultMovies?
    suspend fun getMoviesNow(): ResultMovies?
    suspend fun getMoviesPopular(): ResultMovies?
    suspend fun getMovieDetails(id: Int): Movie?
    suspend fun getMovieCredits(id: Int): Credits?
    suspend fun getSearchMoviesPagedResult(query: String, page: Int): ResultMovies?

    suspend fun getTvNow(): ResultTv?
    suspend fun getTvPopular(): ResultTv?
    suspend fun getTvDetails(id: Int): TvShow?
    suspend fun getTvCredits(id: Int): Credits?
    suspend fun getSearchTvPagedResult(query: String, page: Int): ResultTv?

    suspend fun getPerson(person_id: Int): Person?
    suspend fun getPersonCredits(person_id: Int): PersonCredits?

    fun getFavoriteList(): Flow<List<Favorite>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun removeFavorite(id: Int)
    suspend fun deleteFavorite(favorite: Favorite)
}