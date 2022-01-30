package com.ruslangrigoriev.topmovie.presentation.video

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.video.Video
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.launch
import timber.log.Timber

class VideoViewModel(val repository: Repository) : ViewModel() {

    private val _videoLD = MutableLiveData<List<Video>>()
    val videoLD: LiveData<List<Video>>
        get() = _videoLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    fun fetchMovieVideoData(id: Int) =
        viewModelScope.launch {
            Timber.d( "fetchMovieVideoData ID: $id ")
            try {
                _videoLD.postValue(repository.getMovieVideo(id)?.videos)
            } catch (e: Throwable) {
                _errorLD.postValue(e.message)
            }
        }

    fun fetchTvVideoData(id: Int) =
        viewModelScope.launch {
            Timber.d( "fetchTvVideoData ID: $id ")
            try {
                _videoLD.postValue(repository.getTvVideo(id)?.videos)
            } catch (e: Throwable) {
                _errorLD.postValue(e.message)
            }
        }

}