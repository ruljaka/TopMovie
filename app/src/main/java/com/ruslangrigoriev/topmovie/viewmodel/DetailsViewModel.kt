package com.ruslangrigoriev.topmovie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.model.Details.Details
import com.ruslangrigoriev.topmovie.data.model.credits.Cast
import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val id: Int,
) : ViewModel() {

    private val repository = RepositoryImpl
    private val _detailsLD = MutableLiveData<Details>()
    val detailsLD: MutableLiveData<Details> get() = _detailsLD
    private val _castLD = MutableLiveData<List<Cast>>()
    val castLD: MutableLiveData<List<Cast>> get() = _castLD
    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String> get() = _errorLD

    fun getDetails(id: Int) = viewModelScope.launch {
        Log.d("TAG", "getDetails -> Movie ID: $id")
        repository.getDetails(id).let { response ->
            if (response.isSuccessful) {
                _detailsLD.postValue(response.body())
            } else {
                _errorLD.postValue(response.code().toString())
            }
        }
    }

    fun getCast(id: Int) = viewModelScope.launch {
        repository.getCast(id).let { response ->
            if (response.isSuccessful) {
                _castLD.postValue(response.body()?.cast)
            } else {
                _errorLD.postValue(response.code().toString())
            }
        }
    }
}