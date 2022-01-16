package com.ruslangrigoriev.topmovie.presentation.tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.launch

class TvViewModel(val repository: Repository) : ViewModel() {

    private val _popularLD = MutableLiveData<List<TvShow>>()
    val popularLD: LiveData<List<TvShow>>
        get() = _popularLD

    private val _nowLD = MutableLiveData<List<TvShow>>()
    val nowLD: LiveData<List<TvShow>>
        get() = _nowLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    init {
        if(_popularLD.value == null){
            fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            Log.d(TAG, "fetchTvNow -> TvViewModel")
            _isLoadingLiveData.value = true
            try {
                _nowLD.postValue(repository.getTvNow()?.tvShows)
                _popularLD.postValue(repository.getTvPopular()?.tvShows)
                _isLoadingLiveData.value = false
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                _isLoadingLiveData.value = false
            }
        }
    }

}