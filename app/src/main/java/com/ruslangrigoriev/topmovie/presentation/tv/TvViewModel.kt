package com.ruslangrigoriev.topmovie.presentation.tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TvViewModel(val repository: Repository) : ViewModel() {

    private val _nowLD = MutableLiveData<List<TvShow>>()
    val nowLD: LiveData<List<TvShow>>
        get() = _nowLD

    private val _popularLD = MutableLiveData<List<TvShow>>()
    val popularLD: LiveData<List<TvShow>>
        get() = _popularLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    init {
        if (_popularLD.value == null) {
            fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            Timber.d( "fetchData")
            _isLoadingLiveData.value = true
            try {
                val listNow =
                    withContext(Dispatchers.IO) { repository.getTvNow()?.tvShows }
                val listPopular =
                    withContext(Dispatchers.IO) { repository.getTvPopular()?.tvShows }
                listNow?.let { _nowLD.postValue(it) }
                listPopular?.let { _popularLD.postValue(it) }
                _isLoadingLiveData.value = false
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                _isLoadingLiveData.value = false
            }
        }
    }

}