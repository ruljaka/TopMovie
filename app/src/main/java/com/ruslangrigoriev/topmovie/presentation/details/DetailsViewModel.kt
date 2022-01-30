package com.ruslangrigoriev.topmovie.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.FavoriteCredentials
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<DetailsScreenViewState>()
    val viewState: LiveData<DetailsScreenViewState>
        get() = _viewState

    fun fetchDetailsData(mediaID: Int, sourceType: String) {
        Timber.d("fetchDetailsData ID: $mediaID ")
        viewModelScope.launch {
            _viewState.value = DetailsScreenViewState.Loading
            try {
                if (sourceType == MOVIE_TYPE) {
                    val details = async { repository.getMovieDetails(mediaID) }
                    val listCast = async { repository.getMovieCredits(mediaID)?.cast }
                    _viewState.postValue(
                        DetailsScreenViewState.SuccessMovie(
                            details.await(),
                            listCast.await()
                        )
                    )
                } else {
                    val details = async { repository.getTvDetails(mediaID) }
                    val listCast = async { repository.getTvCredits(mediaID)?.cast }
                    _viewState.postValue(
                        DetailsScreenViewState.SuccessTvShow(
                            details.await(),
                            listCast.await()
                        )
                    )
                }
            } catch (e: Throwable) {
                _viewState.postValue(DetailsScreenViewState.Failure(e.message))
            }
        }
    }



    fun markFavorite(mediaType: String, media_id: Int){
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
            }catch (e: Throwable){
                _viewState.postValue(DetailsScreenViewState.Failure(e.message))
            }
        }
    }
}