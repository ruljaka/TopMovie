package com.ruslangrigoriev.topmovie.data.repository

import androidx.lifecycle.MutableLiveData
import com.ruslangrigoriev.topmovie.data.model.Details.Details
import com.ruslangrigoriev.topmovie.data.model.Movie
import com.ruslangrigoriev.topmovie.data.model.Result
import com.ruslangrigoriev.topmovie.data.model.credits.Cast
import com.ruslangrigoriev.topmovie.data.model.credits.Credits
import retrofit2.Response

interface Repository {
    suspend fun getTrending(): Response<Result>
    suspend fun getDetails(id: Int) : Response<Details>
    suspend fun getCast(id: Int) : Response<Credits>
    suspend fun getSearchResult(query: String) : Response<Result>
}