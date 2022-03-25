package com.ruslangrigoriev.topmovie.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.domain.repository.MovieRepository
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor
    (private val movieRepository: MovieRepository) : ViewModel() {

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ResultState.Failure(throwable.message))
    }

    fun fetchMoviesData() {
        Timber.d("fetchMoviesData ")
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ResultState.Loading
            val listNow = async { movieRepository.getMoviesNow() }
            val listPopular = async { movieRepository.getMoviesPopular() }
            _viewState.postValue(
                ResultState.Success(
                    listNow = listNow.await(),
                    listPopular = listPopular.await()
                )
            )
        }
    }

}
