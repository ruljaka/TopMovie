package com.ruslangrigoriev.topmovie.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.data.paging.MoviePagingSource
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.utils.PagingType
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber


class MovieViewModel(val repository: Repository) : ViewModel() {

    private lateinit var _trendingFlowData: Flow<PagingData<Movie>>
    val trendingFlowData: Flow<PagingData<Movie>>
        get() = _trendingFlowData

    private val _viewState = MutableLiveData<MovieScreenViewState>()
    val viewState: LiveData<MovieScreenViewState>
        get() = _viewState

    init {
        if(_viewState.value == null){
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
        Timber.d("fetchMoviesData ")
        viewModelScope.launch() {
            _viewState.value = MovieScreenViewState.Loading
            try {
                val listNow = async { repository.getMoviesNow()?.movies }
                val listPopular = async { repository.getMoviesPopular()?.movies }
                _viewState.postValue(
                    MovieScreenViewState.Success(
                        listNow.await(),
                        listPopular.await()
                    )
                )
            } catch (e: Exception) {
                _viewState.postValue(MovieScreenViewState.Failure(e.message))
            }
        }
    }

}
