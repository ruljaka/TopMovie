package com.ruslangrigoriev.topmovie.data.remote

import com.ruslangrigoriev.topmovie.domain.model.FavoriteCredentials
import com.ruslangrigoriev.topmovie.domain.model.ResponseObject
import com.ruslangrigoriev.topmovie.domain.model.auth.*
import com.ruslangrigoriev.topmovie.domain.model.credits.CreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCreditsResponse
import com.ruslangrigoriev.topmovie.domain.model.profile.User
import com.ruslangrigoriev.topmovie.domain.model.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.model.video.VideoResponse
import com.ruslangrigoriev.topmovie.domain.utils.API_KEY
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("trending/movie/day?")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("movie/now_playing?")
    suspend fun getMoviesNow(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("movie/popular?")
    suspend fun getMoviesPopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<Movie>

    @GET("movie/{id}/credits?")
    suspend fun getMovieCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<CreditsResponse>

    @GET("search/movie?")
    suspend fun searchPagedMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("tv/on_the_air?")
    suspend fun getTVNow(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @GET("tv/popular?")
    suspend fun getTvPopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @GET("tv/{id}")
    suspend fun getTvDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<TvShow>

    @GET("tv/{id}/credits?")
    suspend fun getTvCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<CreditsResponse>

    @GET("search/tv?")
    suspend fun searchPagedTvShow(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<TvResponse>

    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") person_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<Person>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonCredits(
        @Path("person_id") person_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<PersonCreditsResponse>

    @GET("movie/{movie_id}/videos?")
    suspend fun getMovieVideos(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<VideoResponse>

    @GET("tv/{tv_id}/videos?")
    suspend fun getTvVideos(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<VideoResponse>

    @GET("authentication/token/new")
    suspend fun createRequestToken(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<RequestToken>

    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Body authCredentials: AuthCredentials
    ): Response<RequestToken>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body token: Token
    ): Response<Session>

    @GET("account")
    suspend fun getUser(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String,
    ): Response<User>

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Path("account_id") account_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTvShow(
        @Path("account_id") account_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") account_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ) :Response<MovieResponse>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTvShow(
        @Path("account_id") account_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String,
        @Query("page") page: Int = 1,
    ): Response<TvResponse>

    @POST("account/{account_id}/favorite")
    suspend fun markAsFavorite(
        @Path("account_id") account_id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String,
        @Body favoriteCredentials: FavoriteCredentials
    ) : Response<ResponseObject>

}