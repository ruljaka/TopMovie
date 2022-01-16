package com.ruslangrigoriev.topmovie.data.api

import com.ruslangrigoriev.topmovie.domain.model.credits.Credits
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.ResultMovies
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import com.ruslangrigoriev.topmovie.domain.model.tv.ResultTv
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.API
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/day?")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = API,
        @Query("page") page: Int,
    ): Response<ResultMovies>

    @GET("movie/now_playing?")
    suspend fun getMoviesNow(
        @Query("api_key") apiKey: String = API,
        @Query("page") page: Int = 1,
    ): Response<ResultMovies>

    @GET("movie/popular?")
    suspend fun getMoviesPopular(
        @Query("api_key") apiKey: String = API,
        @Query("page") page: Int = 1,
    ): Response<ResultMovies>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<Movie>

    @GET("movie/{id}/credits?")
    suspend fun getMovieCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<Credits>

    @GET("search/movie?")
    suspend fun searchPagedMovie(
        @Query("api_key") apiKey: String = API,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<ResultMovies>

    @GET("tv/on_the_air?")
    suspend fun getTVNow(
        @Query("api_key") apiKey: String = API,
        @Query("page") page: Int = 1,
    ): Response<ResultTv>

    @GET("tv/popular?")
    suspend fun getTvPopular(
        @Query("api_key") apiKey: String = API,
        @Query("page") page: Int = 1,
    ): Response<ResultTv>

    @GET("tv/{id}")
    suspend fun getTvDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<TvShow>

    @GET("tv/{id}/credits?")
    suspend fun getTvCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<Credits>

    @GET("search/tv?")
    suspend fun searchPagedTvShow(
        @Query("api_key") apiKey: String = API,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<ResultTv>

    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") person_id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<Person>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonCredits(
        @Path("person_id") person_id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<PersonCredits>

}