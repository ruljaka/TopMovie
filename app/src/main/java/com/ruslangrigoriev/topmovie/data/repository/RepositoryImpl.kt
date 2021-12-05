package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.domain.model.movies.Result
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import retrofit2.Response

class RepositoryImpl(private var apiService: ApiService) : Repository {

    override suspend fun getPagedTrending(page: Int): Response<Result> =
        apiService.getPagedTrending(page = page)

    override suspend fun getDetails(id: Int) = apiService.getDetails(id)

    override suspend fun getCast(id: Int) = apiService.getCast(id)

    override suspend fun getSearchPagedResult(query: String, page: Int): Response<Result> =
        apiService.searchPagedMovie(query = query, page = page)

    override suspend fun getPerson(person_id: Int): Response<Person> =
        apiService.getPerson(person_id)

    override suspend fun getPersonCredits(person_id: Int): Response<PersonCredits> =
        apiService.getPersonCredits(person_id)
}