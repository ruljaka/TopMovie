package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.model.Details.Details
import com.ruslangrigoriev.topmovie.data.model.Result
import com.ruslangrigoriev.topmovie.data.model.credits.Credits
import retrofit2.Response

interface Repository {
    suspend fun getPagedTrending(page: Int): Response<Result>
    suspend fun getDetails(id: Int): Response<Details>
    suspend fun getCast(id: Int): Response<Credits>
    suspend fun getSearchPagedResult(query: String, page: Int): Response<Result>
}