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
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber


class MovieViewModel(val repository: Repository) : ViewModel() {

    private lateinit var _trendingFlowData: Flow<PagingData<Movie>>
    val trendingFlowData: Flow<PagingData<Movie>>
        get() = _trendingFlowData

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ResultState.Failure(throwable.message))
    }

    private fun fetchMoviesTrending() {
        _trendingFlowData =
            Pager(PagingConfig(pageSize = 20)) {
                MoviePagingSource<Movie>(repository = repository, type = PagingType.MOVIE_FLOW)
            }.flow.cachedIn(viewModelScope)
    }

    fun fetchMoviesData() {
        Timber.d("fetchMoviesData ")
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ResultState.Loading
            val listNow = async { repository.getMoviesNow() }
            val listPopular = async { repository.getMoviesPopular() }
            _viewState.postValue(
                ResultState.Success(
                    listNow = listNow.await(),
                    listPopular= listPopular.await()
                )
            )

        }
    }

}
