package com.ruslangrigoriev.topmovie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruslangrigoriev.topmovie.api.ApiFactory
import com.ruslangrigoriev.topmovie.pojo.Details.Details
import com.ruslangrigoriev.topmovie.pojo.credits.Cast
import com.ruslangrigoriev.topmovie.pojo.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    val trendingMoviesLD = MutableLiveData<List<Movie>>()
    val detailsLD = MutableLiveData<Details>()
    val castLD = MutableLiveData<List<Cast>>()
    val searchMoviesLD = MutableLiveData<List<Movie>>()
    val errorLD = MutableLiveData<String>()

    private var compositeDisposable: CompositeDisposable? = null

    fun getMovies() {
        val apiService = ApiFactory.getApiService
        val trendingDisposable = apiService.getTrending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                trendingMoviesLD.value = it.movies
                Log.d("TAG", it.movies.size.toString() + " trending size")
            }, {
                errorLD.value = it.message
                Log.d("TAG", it.message.toString())
            })
        compositeDisposable?.add(trendingDisposable)
    }

    fun getDetails(id: Int) {
        val apiService = ApiFactory.getApiService
        compositeDisposable = CompositeDisposable()
        val detailsDisposable = apiService.getDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                detailsLD.value = it
                Log.d("TAG", it.title)
            }, {
                errorLD.value = it.message
                Log.d("TAG", it.message.toString())
            })
        compositeDisposable?.add(detailsDisposable)
    }

    fun getCast(id: Int) {
        val apiService = ApiFactory.getApiService
        compositeDisposable = CompositeDisposable()
        val castDisposable = apiService.getCredits(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                castLD.value = it.cast
                Log.d("TAG", it.cast.size.toString() + " cast size")
            }, {
                errorLD.value = it.message
                Log.d("TAG", it.message.toString())
            })
        compositeDisposable?.add(castDisposable)
    }

    fun getSearchResult(query:String){
        val apiService = ApiFactory.getApiService
        compositeDisposable = CompositeDisposable()
        val searchDisposable = apiService.searchMovie(query = query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchMoviesLD.value = it.movies
                val logList = mutableListOf<String>()
                for(movie in it.movies){
                    logList.add(movie.title)
                }
                Log.d("TAG", logList.toString())
            }, {
                errorLD.value = it.message
                Log.d("TAG", it.message.toString())
            })
        compositeDisposable?.add(searchDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }


}