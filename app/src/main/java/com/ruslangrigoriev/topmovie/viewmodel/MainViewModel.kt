package com.ruslangrigoriev.topmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruslangrigoriev.topmovie.domain.RepositoryImpl
import com.ruslangrigoriev.topmovie.pojo.Details.Details
import com.ruslangrigoriev.topmovie.pojo.Movie
import com.ruslangrigoriev.topmovie.pojo.credits.Cast
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : ViewModel() {

    private val repository = RepositoryImpl

    lateinit var trendingMoviesLD: MutableLiveData<List<Movie>>
    lateinit var detailsLD: MutableLiveData<Details>
    lateinit var castLD: MutableLiveData<List<Cast>>
    lateinit var searchMoviesLD: MutableLiveData<List<Movie>>

    private var compositeDisposable: CompositeDisposable? = null

    fun getMovies() {
        trendingMoviesLD = repository.getMovies()
    }

    fun getDetails(id: Int) {
        detailsLD = repository.getDetails(id)
    }

    fun getCast(id: Int) {
        castLD = repository.getCast(id)
    }

    fun getSearchResult(query: String) {
        searchMoviesLD = repository.getSearchResult(query)
    }

    override fun onCleared() {
        super.onCleared()
        repository.disposeDisposable()
    }

}