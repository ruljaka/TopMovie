package com.ruslangrigoriev.topmovie.viewmodel

import androidx.lifecycle.*
import com.ruslangrigoriev.topmovie.data.model.Details.Details
import com.ruslangrigoriev.topmovie.data.model.Movie
import com.ruslangrigoriev.topmovie.data.model.credits.Cast
import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = RepositoryImpl

//    lateinit var trendingMoviesLD: MutableLiveData<List<Movie>>
//    lateinit var detailsLD: MutableLiveData<Details>
//    lateinit var castLD: MutableLiveData<List<Cast>>
//    lateinit var searchMoviesLD: MutableLiveData<List<Movie>>
//    val errorLD = repository.errorLD

    private val _trendingMoviesLD =  MutableLiveData<List<Movie>>()
    val trendingMoviesLD: LiveData<List<Movie>> get() = _trendingMoviesLD

    private val _detailsLD = MutableLiveData<Details>()
    val detailsLD: MutableLiveData<Details> get() = _detailsLD

    private val _castLD = MutableLiveData<List<Cast>>()
    val castLD: MutableLiveData<List<Cast>> get() = _castLD

    private val _searchMoviesLD = MutableLiveData<List<Movie>>()
    val searchMoviesLD: MutableLiveData<List<Movie>> get() = _searchMoviesLD

    private val _errorLD =  MutableLiveData<String>()
    val errorLD: LiveData<String> get() = _errorLD


    fun getTrendingMovies() = viewModelScope.launch {
        repository.getTrending().let { response->
            if(response.isSuccessful){
                _trendingMoviesLD.postValue(response.body()?.movies)
            } else{
                _errorLD.postValue(response.code().toString())
            }
        }
    }

    fun getDetails(id: Int) = viewModelScope.launch {
        repository.getDetails(id).let {response->
            if(response.isSuccessful){
                _detailsLD.postValue(response.body())
            } else{
                _errorLD.postValue(response.code().toString())
            }
        }
    }

    fun getCast(id: Int) = viewModelScope.launch {
        repository.getCast(id).let {response->
            if(response.isSuccessful){
                _castLD.postValue(response.body()?.cast)
            } else{
                _errorLD.postValue(response.code().toString())
            }
        }
    }

    fun getSearchResult(query: String) = viewModelScope.launch {
        repository.getSearchResult(query = query).let {response->
            if(response.isSuccessful){
                _searchMoviesLD.postValue(response.body()?.movies)
            } else{
                _errorLD.postValue(response.code().toString())
            }
        }
    }





//    fun getMovies() {
//        trendingMoviesLD = repository.getMovies()
//    }

//    fun getDetails(id: Int) {
//        detailsLD = repository.getDetails(id)
//    }

//    fun getCast(id: Int) {
//        castLD = repository.getCast(id)
//    }
//
//    fun getSearchResult(query: String) {
//        searchMoviesLD = repository.getSearchResult(query)
//    }

    override fun onCleared() {
        super.onCleared()
        //repository.disposeDisposable()
    }

}