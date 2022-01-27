package com.ruslangrigoriev.topmovie.presentation.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.data.paging.MoviePagingSource
import com.ruslangrigoriev.topmovie.domain.utils.PagingType
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


class MovieViewModel(val repository: Repository) : ViewModel() {

    private lateinit var _trendingFlowData: Flow<PagingData<Movie>>
    val trendingFlowData: Flow<PagingData<Movie>>
        get() = _trendingFlowData

    private val _popularLD = MutableLiveData<List<Movie>>()
    val popularLD: LiveData<List<Movie>>
        get() = _popularLD

    private val _nowLD = MutableLiveData<List<Movie>>()
    val nowLD: LiveData<List<Movie>>
        get() = _nowLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    init {
        if ((_nowLD.value == null)
            || (_popularLD.value == null)
        ) {
            fetchMoviesData()
        }
    }

    private fun fetchMoviesTrending() {
        _trendingFlowData =
            Pager(PagingConfig(pageSize = 20)) {
                MoviePagingSource<Movie>(repository = repository, type = PagingType.MOVIE_FLOW)
            }.flow.cachedIn(viewModelScope)
    }

    private fun fetchMoviesData() {
        Timber.d( "fetchMoviesData ")
        viewModelScope.launch() {
            _isLoadingLiveData.postValue(true)
            try {
                val listNow =
                    withContext(Dispatchers.IO) { repository.getMoviesNow()?.movies }
                val listPopular =
                    withContext(Dispatchers.IO) { repository.getMoviesPopular()?.movies }
                listNow?.let{_nowLD.postValue(it)}
                listPopular?.let{_popularLD.postValue(it)}
                _isLoadingLiveData.postValue(false)
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                _isLoadingLiveData.postValue(false)
            }
        }
    }

}
