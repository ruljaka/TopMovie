package com.ruslangrigoriev.topmovie.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.domain.repository.MovieRepository
import com.ruslangrigoriev.topmovie.domain.repository.TvShowRepository
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import com.ruslangrigoriev.topmovie.domain.utils.TV_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VideoViewModel
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    fun fetchVideoData(id: Int, mediaType: String) =
        viewModelScope.launch {
            _viewState.value = ResultState.Loading
            Timber.d("fetchVideoData ID: $id ")
            try {
                when (mediaType) {
                    MOVIE_TYPE -> {
                        _viewState.postValue(
                            ResultState.Success(
                                listVideo = movieRepository.getMovieVideo(id)
                            )
                        )
                    }
                    TV_TYPE -> {
                        _viewState.postValue(
                            ResultState.Success(
                                listVideo = tvShowRepository.getTvVideo(id)
                            )
                        )
                    }
                }
            } catch (e: Throwable) {
                _viewState.postValue(ResultState.Failure(e.message))
            }
        }
}