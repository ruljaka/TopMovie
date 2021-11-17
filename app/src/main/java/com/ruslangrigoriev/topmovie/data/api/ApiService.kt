package com.ruslangrigoriev.topmovie.data.api

import com.ruslangrigoriev.topmovie.API
import com.ruslangrigoriev.topmovie.data.model.Details.Details
import com.ruslangrigoriev.topmovie.data.model.Result
import com.ruslangrigoriev.topmovie.data.model.credits.Credits
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //trending/all/day?api_key=...
    @GET("trending/all/day?")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = API,
    ): Response<Result>

    //movie/630004?api_key=...
    @GET("movie/{id}")
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<Details>

    //movie/93740/credits?api_key=
    @GET("movie/{id}/credits?")
    suspend fun getCast(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<Credits>

    //search/movie?api_key=...&query=query
    @GET("search/movie?")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = API,
        @Query("query") query: String,
    ): Response<Result>
}