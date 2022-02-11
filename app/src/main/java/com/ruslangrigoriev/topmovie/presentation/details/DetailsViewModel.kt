package com.ruslangrigoriev.topmovie.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.FavoriteCredentials
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ResultState.Failure(throwable.message))
    }

    fun fetchDetailsData(mediaID: Int, mediaType: String) {
        Timber.d("fetchDetailsData ID: $mediaID ")
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ResultState.Loading
            if (mediaType == MOVIE_TYPE) {
                val details = async { repository.getMovieDetails(mediaID) }
                val listCast = async { repository.getMovieCredits(mediaID) }
                _viewState.postValue(
                    ResultState.Success(
                        details = details.await(),
                        listCast = listCast.await()
                    )
                )
            } else {
                val details = async { repository.getTvDetails(mediaID) }
                val listCast = async { repository.getTvCredits(mediaID) }
                _viewState.postValue(
                    ResultState.Success(
                        details = details.await(),
                        listCast = listCast.await()
                    )
                )
            }
        }
    }

    fun markFavorite(mediaType: String, media_id: Int) {
        Timber.d("markFavorite ID: $media_id ")
        viewModelScope.launch {
            try {
                val favoriteCredentials = FavoriteCredentials(
                    mediaType = mediaType,
                    mediaId = media_id,
                    favorite = true
                )
                val response = repository.markFavorite(favoriteCredentials)
                Timber.d(response?.statusMessage)
            } catch (e: Throwable) {
                _viewState.postValue(ResultState.Failure(e.message))
            }
        }
    }
}