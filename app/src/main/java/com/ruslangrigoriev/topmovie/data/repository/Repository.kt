package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.domain.model.credits.Credits
import com.ruslangrigoriev.topmovie.domain.model.details.Details
import com.ruslangrigoriev.topmovie.domain.model.movies.Result
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import retrofit2.Response

interface Repository {
    suspend fun getPagedTrending(page: Int): Response<Result>
    suspend fun getDetails(id: Int): Response<Details>
    suspend fun getCast(id: Int): Response<Credits>
    suspend fun getSearchPagedResult(query: String, page: Int): Response<Result>
    suspend fun getPerson(person_id: Int): Response<Person>
    suspend fun getPersonCredits(person_id: Int): Response<PersonCredits>
}