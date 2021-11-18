package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.api.ApiFactory
import com.ruslangrigoriev.topmovie.data.model.Result
import retrofit2.Response

object RepositoryImpl : Repository {

    private val apiService = ApiFactory.getApiService

    override suspend fun getPagedTrending(page: Int): Response<Result> =
        apiService.getPagedTrending(page = page)

    override suspend fun getDetails(id: Int) = apiService.getDetails(id)
    override suspend fun getCast(id: Int) = apiService.getCast(id)

    override suspend fun getSearchPagedResult(query: String, page: Int): Response<Result> =
        apiService.searchPagedMovie(query = query, page = page)
}