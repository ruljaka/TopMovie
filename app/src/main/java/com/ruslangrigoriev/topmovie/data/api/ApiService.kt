package com.ruslangrigoriev.topmovie.data.api

import com.ruslangrigoriev.topmovie.domain.model.credits.Credits
import com.ruslangrigoriev.topmovie.domain.model.details.Details
import com.ruslangrigoriev.topmovie.domain.model.movies.Result
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import com.ruslangrigoriev.topmovie.domain.utils.API
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //trending/all/day?api_key=...
    @GET("trending/movie/day?")
    suspend fun getPagedTrending(
        @Query("api_key") apiKey: String = API,
        @Query("page") page: Int,
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
    suspend fun searchPagedMovie(
        @Query("api_key") apiKey: String = API,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<Result>

    //person/1136406?api_key=...
    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") person_id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<Person>

    //person/1136406/movie_credits?api_key=...
    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonCredits(
        @Path("person_id") person_id: Int,
        @Query("api_key") apiKey: String = API,
    ): Response<PersonCredits>

}