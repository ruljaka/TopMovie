package com.ruslangrigoriev.topmovie.domain

import androidx.lifecycle.MutableLiveData
import com.ruslangrigoriev.topmovie.pojo.Details.Details
import com.ruslangrigoriev.topmovie.pojo.Movie
import com.ruslangrigoriev.topmovie.pojo.credits.Cast

interface Repository {
    fun getMovies(): MutableLiveData<List<Movie>>
    fun getDetails(id: Int) : MutableLiveData<Details>
    fun getCast(id: Int) : MutableLiveData<List<Cast>>
    fun getSearchResult(query: String) : MutableLiveData<List<Movie>>
}