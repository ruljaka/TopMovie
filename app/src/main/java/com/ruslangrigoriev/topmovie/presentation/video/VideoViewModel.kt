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

class VideoViewModel(val repository: Repository) : ViewModel() {

    private val _movieVideoLD = MutableLiveData<List<Video>>()
    val movieVideoLD: LiveData<List<Video>>
        get() = _movieVideoLD

    private val _tvVideoLD = MutableLiveData<List<Video>>()
    val tvVideoLD: LiveData<List<Video>>
        get() = _tvVideoLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    fun fetchMovieVideoData(id: Int) =
        viewModelScope.launch {
            Log.d(TAG, "fetchMovieVideoData ID: $id -> VideoViewModel")
            try {
                _movieVideoLD.postValue(repository.getMovieVideo(id)?.videos)

            } catch (e: Exception) {
                _errorLD.postValue(e.message)
            }
        }

    fun fetchTvVideoData(id: Int) =
        viewModelScope.launch {
            Log.d(TAG, "fetchTvVideoData ID: $id -> VideoViewModel")
            try {
                _tvVideoLD.postValue(repository.getTvVideo(id)?.videos)
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
            }
        }

}