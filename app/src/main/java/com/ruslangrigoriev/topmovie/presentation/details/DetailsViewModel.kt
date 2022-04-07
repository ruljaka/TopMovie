package com.ruslangrigoriev.topmovie.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.domain.repository.MovieRepository
import com.ruslangrigoriev.topmovie.domain.repository.TvShowRepository
import com.ruslangrigoriev.topmovie.domain.repository.UserRepository
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _isRated = MutableLiveData<Boolean>()
    val isRated: LiveData<Boolean>
        get() = _isRated


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ResultState.Failure(throwable.message))
    }

    fun fetchDetailsData(mediaID: Int, mediaType: String) {
        Timber.d("fetchDetailsData ID: $mediaID ")
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ResultState.Loading
            if (mediaType == MOVIE_TYPE) {
                val details = async { movieRepository.getMovieDetails(mediaID) }
                val listCast = async { movieRepository.getMovieCredits(mediaID) }
                _viewState.postValue(
                    ResultState.Success(
                        details = details.await(),
                        listCast = listCast.await()
                    )
                )
            } else {
                val details = async { tvShowRepository.getTvDetails(mediaID) }
                val listCast = async { tvShowRepository.getTvCredits(mediaID) }
                _viewState.postValue(
                    ResultState.Success(
                        details = details.await(),
                        listCast = listCast.await()
                    )
                )
            }
        }
    }

    fun markFavorite(mediaType: String, mediaID: Int) {
        Timber.d("markFavorite ID: $mediaID  $mediaType")
        viewModelScope.launch(exceptionHandler) {
            val response = userRepository.markFavorite(mediaType, mediaID)
            checkIsFavoriteAndRated(mediaID)
            Timber.d(response?.statusMessage)
        }
    }

    fun markRated(mediaType: String, mediaID: Int, value: String) {
        Timber.d("markRated ID: $mediaID ")
        viewModelScope.launch(exceptionHandler) {
            val response = userRepository.markRated(mediaType, mediaID,value)
            _isRated.postValue(true)
            Timber.d(response?.statusMessage)
        }
    }

    fun checkIsFavoriteAndRated(mediaID :Int){
        viewModelScope.launch {
            _isFavorite.postValue(userRepository.checkIsFavorite(mediaID))
            _isRated.postValue(userRepository.checkIsRated(mediaID))
        }
    }
}