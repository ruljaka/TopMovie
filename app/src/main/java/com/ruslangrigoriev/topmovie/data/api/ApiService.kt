package com.ruslangrigoriev.topmovie.data.api

import com.google.gson.JsonObject
import com.ruslangrigoriev.topmovie.data.api.dto.auth.AuthCredentials
import com.ruslangrigoriev.topmovie.data.api.dto.auth.RequestToken
import com.ruslangrigoriev.topmovie.data.api.dto.auth.Session
import com.ruslangrigoriev.topmovie.data.api.dto.auth.Token
import com.ruslangrigoriev.topmovie.data.api.dto.credits.CreditsResponse
import com.ruslangrigoriev.topmovie.data.api.dto.movies.Movie
import com.ruslangrigoriev.topmovie.data.api.dto.movies.MovieResponse
import com.ruslangrigoriev.topmovie.data.api.dto.person.Person
import com.ruslangrigoriev.topmovie.data.api.dto.person.PersonCreditsResponse
import com.ruslangrigoriev.topmovie.data.api.dto.profile.User
import com.ruslangrigoriev.topmovie.data.api.dto.tv.TvResponse
import com.ruslangrigoriev.topmovie.data.api.dto.tv.TvShow
import com.ruslangrigoriev.topmovie.data.api.dto.video.VideoResponse
import com.ruslangrigoriev.topmovie.data.api.dto.favorite.FavoriteCredentials
import com.ruslangrigoriev.topmovie.data.api.dto.favorite.FavoriteResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("trending/movie/day?")
    suspend fun getTrending(
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("movie/now_playing?")
    suspend fun getMoviesNow(
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("movie/popular?")
    suspend fun getMoviesPopular(
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
    ): Response<Movie>

    @GET("movie/{id}/credits?")
    suspend fun getMovieCredits(
        @Path("id") id: Int,
    ): Response<CreditsResponse>

    @GET("search/movie?")
    suspend fun searchPagedMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("tv/on_the_air?")
    suspend fun getTVNow(
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @GET("tv/popular?")
    suspend fun getTvPopular(
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @GET("tv/{id}")
    suspend fun getTvDetails(
        @Path("id") id: Int,
    ): Response<TvShow>

    @GET("tv/{id}/credits?")
    suspend fun getTvCredits(
        @Path("id") id: Int,
    ): Response<CreditsResponse>

    @GET("search/tv?")
    suspend fun searchPagedTvShow(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<TvResponse>

    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") person_id: Int,
    ): Response<Person>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonCredits(
        @Path("person_id") person_id: Int,
    ): Response<PersonCreditsResponse>

    @GET("movie/{movie_id}/videos?")
    suspend fun getMovieVideos(
        @Path("movie_id") movie_id: Int,
    ): Response<VideoResponse>

    @GET("tv/{tv_id}/videos?")
    suspend fun getTvVideos(
        @Path("tv_id") tv_id: Int,
    ): Response<VideoResponse>

    @GET("authentication/token/new")
    suspend fun createRequestToken(
    ): Response<RequestToken>

    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestToken(
        @Body authCredentials: AuthCredentials
    ): Response<RequestToken>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Body token: Token
    ): Response<Session>

    @GET("account")
    suspend fun getUser(
        @Query("session_id") session_id: String,
    ): Response<User>

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Path("account_id") account_id: Int,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTvShow(
        @Path("account_id") account_id: Int,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") account_id: Int,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTvShow(
        @Path("account_id") account_id: Int,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @POST("account/{account_id}/favorite")
    suspend fun markAsFavorite(
        @Path("account_id") account_id: Int,
        @Query("session_id") session_id: String,
        @Body favoriteCredentials: FavoriteCredentials
    ): Response<FavoriteResponse>

    @POST("movie/{movie_id}/rating")
    suspend fun markAsRatedMovie(
        @Path("movie_id") movie_id: Int,
        @Query("session_id") session_id: String,
        @Body body: JsonObject
    ): Response<FavoriteResponse>

    @POST("tv/{tv_id}/rating")
    suspend fun markAsRatedTvShow(
        @Path("tv_id") tv_id: Int,
        @Query("session_id") session_id: String,
        @Body body: JsonObject
    ): Response<FavoriteResponse>


}