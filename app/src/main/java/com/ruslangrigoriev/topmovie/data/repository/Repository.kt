package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.model.credits.CreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.model.video.VideoResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMoviesTrending(page: Int): MovieResponse?

    suspend fun getMoviesNow(): MovieResponse?
    suspend fun getMoviesPopular(): MovieResponse?
    suspend fun getMovieDetails(id: Int): Movie?
    suspend fun getMovieCredits(id: Int): CreditsResponse?
    suspend fun getSearchMoviesPagedResult(query: String, page: Int): MovieResponse?

    suspend fun getTvNow(): TvResponse?
    suspend fun getTvPopular(): TvResponse?
    suspend fun getTvDetails(id: Int): TvShow?
    suspend fun getTvCredits(id: Int): CreditsResponse?
    suspend fun getSearchTvPagedResult(query: String, page: Int): TvResponse?

    suspend fun getPerson(person_id: Int): Person?
    suspend fun getPersonCredits(person_id: Int): PersonCreditsResponse?

    suspend fun getMovieVideo(movie_id: Int): VideoResponse?
    suspend fun getTvVideo(movie_id: Int): VideoResponse?

    fun getFavoriteList(): Flow<List<Favorite>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun removeFavorite(id: Int)
    suspend fun deleteFavorite(favorite: Favorite)
}