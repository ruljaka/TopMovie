package com.ruslangrigoriev.topmovie.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor
    (val repository: Repository) : ViewModel() {

    private lateinit var _trendingFlowData: Flow<PagingData<Media>>
    val trendingFlowData: Flow<PagingData<Media>>
        get() = _trendingFlowData

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ResultState.Failure(throwable.message))
    }

//    private fun fetchMoviesTrending() {
//        _trendingFlowData =
//            Pager(PagingConfig(pageSize = 20)) {
//                MoviePagingSource(repository = repository, type = PagingType.MOVIE_FLOW)
//            }.flow.cachedIn(viewModelScope)
//    }

    fun fetchMoviesData() {
        Timber.d("fetchMoviesData ")
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ResultState.Loading
            val listNow = async { repository.getMoviesNow() }
            val listPopular = async { repository.getMoviesPopular() }
            _viewState.postValue(
                ResultState.Success(
                    listNow = listNow.await(),
                    listPopular = listPopular.await()
                )
            )

        }
    }

}
