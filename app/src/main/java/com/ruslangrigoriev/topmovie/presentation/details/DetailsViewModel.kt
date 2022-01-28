package com.ruslangrigoriev.topmovie.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<DetailsScreenViewState>()
    val viewState: LiveData<DetailsScreenViewState>
        get() = _viewState

    fun fetchDetailsData(id: Int, sourceType: String) {
        Timber.d("fetchDetailsData ID: $id ")
        viewModelScope.launch {
            _viewState.value = DetailsScreenViewState.Loading
            try {
                if (sourceType == MOVIE_TYPE) {
                    val details = async { repository.getMovieDetails(id) }
                    val listCast = async { repository.getMovieCredits(id)?.cast }
                    _viewState.postValue(
                        DetailsScreenViewState.SuccessMovie(
                            details.await(),
                            listCast.await()
                        )
                    )
                } else {
                    val details = async { repository.getTvDetails(id) }
                    val listCast = async { repository.getTvCredits(id)?.cast }
                    _viewState.postValue(
                        DetailsScreenViewState.SuccessTvShow(
                            details.await(),
                            listCast.await()
                        )
                    )
                }
            } catch (e: Exception) {
                _viewState.postValue(DetailsScreenViewState.Failure(e.message))
            }
        }
    }
}