package com.ruslangrigoriev.topmovie.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.TV_TYPE
import kotlinx.coroutines.launch
import timber.log.Timber

class VideoViewModel(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<ResultVideoState>()
    val viewState: LiveData<ResultVideoState>
        get() = _viewState

    fun fetchVideoData(id: Int, mediaType: String) =
        viewModelScope.launch {
            _viewState.value = ResultVideoState.Loading
            Timber.d("fetchVideoData ID: $id ")
            try {
                when (mediaType) {
                    MOVIE_TYPE -> {
                        _viewState.postValue(
                            ResultVideoState.Success(
                                repository.getMovieVideo(id)
                            )
                        )
                    }
                    TV_TYPE -> {
                        _viewState.postValue(
                            ResultVideoState.Success(
                                repository.getTvVideo(id)
                            )
                        )
                    }
                }
            } catch (e: Throwable) {
                _viewState.postValue(ResultVideoState.Failure(e.message))
            }
        }
}