package com.ruslangrigoriev.topmovie.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.launch

class DetailsViewModel(val repository: Repository) : ViewModel() {

    private val _movieDetailsLD = MutableLiveData<Movie>()
    val movieDetailsLD: LiveData<Movie>
        get() = _movieDetailsLD

    private val _movieCastLD = MutableLiveData<List<Cast>>()
    val movieCastLD: LiveData<List<Cast>>
        get() = _movieCastLD

    private val _tvDetailsLD = MutableLiveData<TvShow>()
    val tvDetailsLD: LiveData<TvShow>
        get() = _tvDetailsLD

    private val _tvCastLD = MutableLiveData<List<Cast>>()
    val tvCastLD: LiveData<List<Cast>>
        get() = _tvCastLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    fun fetchMovieDetailsData(id: Int) {
        Log.d(TAG, "fetchMovieDetailsData ID: $id -> DetailsViewModel")
        viewModelScope.launch {
            _isLoadingLiveData.value = true
            try {
                _movieDetailsLD.postValue(repository.getMovieDetails(id))
                _movieCastLD.postValue(repository.getMovieCredits(id)?.cast)
                _isLoadingLiveData.value = false
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                _isLoadingLiveData.value = false
            }
        }
    }

    fun fetchTvDetailsData(id: Int) =
        viewModelScope.launch {
            _isLoadingLiveData.value = true
            Log.d(TAG, "fetchTvDetailsData ID: $id -> DetailsViewModel")
            try {
                _tvDetailsLD.postValue(repository.getTvDetails(id))
                _tvCastLD.postValue(repository.getTvCredits(id)?.cast)
                _isLoadingLiveData.value = false
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                _isLoadingLiveData.value = false
            }
        }
}