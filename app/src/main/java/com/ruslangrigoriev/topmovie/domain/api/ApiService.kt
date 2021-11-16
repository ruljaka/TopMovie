package com.ruslangrigoriev.topmovie.domain.api

import com.ruslangrigoriev.topmovie.API
import com.ruslangrigoriev.topmovie.pojo.Details.Details
import com.ruslangrigoriev.topmovie.pojo.credits.Credits
import com.ruslangrigoriev.topmovie.pojo.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //trending/all/day?api_key=...
    @GET("trending/all/day?")
    fun getTrending(
        @Query("api_key") apiKey: String = API
    ): Observable<Result>

    //movie/630004?api_key=...
    @GET("movie/{id}")
    fun getDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API
    ): Observable<Details>

    //movie/93740/credits?api_key=
    @GET("movie/{id}/credits?")
    fun getCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API
    ): Observable<Credits>

    //search/movie?api_key=...&query=query
    @GET("search/movie?")
    fun searchMovie(
        @Query("api_key") apiKey: String = API,
        @Query("query") query: String
    ): Observable<Result>
}