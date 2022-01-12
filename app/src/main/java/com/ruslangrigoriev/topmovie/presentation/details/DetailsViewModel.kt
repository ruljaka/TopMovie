package com.ruslangrigoriev.topmovie.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.details.Details
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.launch

class DetailsViewModel(val repository: Repository) : ViewModel() {

    private val _detailsLD = MutableLiveData<Details>()
    val detailsLD: LiveData<Details>
        get() = _detailsLD

    private val _castLD = MutableLiveData<List<Cast>>()
    val castLD: LiveData<List<Cast>>
        get() = _castLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private var isDetailsLoading: Boolean = false
    private var isCastLoading: Boolean = false

    fun getDetails(id: Int) =
        viewModelScope.launch {
            _isLoadingLiveData.value = true
            Log.d(TAG, "getDetails -> Movie ID: $id")
            isDetailsLoading = true
            try {
                _detailsLD.postValue(repository.getDetails(id))
                isDetailsLoading = false
                if (!isCastLoading) {
                    _isLoadingLiveData.value = false
                }
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                isDetailsLoading = false
                if (!isCastLoading) {
                    _isLoadingLiveData.value = false
                }
            }
        }

    fun getCast(id: Int) =
        viewModelScope.launch {
            Log.d(TAG, "getCast -> Movie ID: $id")
            isCastLoading = true
            try {
                _castLD.postValue(repository.getCast(id)?.cast)
                isCastLoading = false
                if (!isDetailsLoading) {
                    _isLoadingLiveData.value = false
                }
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                isCastLoading = false
                if (!isDetailsLoading) {
                    _isLoadingLiveData.value = false
                }
            }
        }

    fun saveToFavorite() {
        detailsLD.value?.let {
            val favorite = Favorite(
                id = it.id,
                originalTitle = it.originalTitle,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage
            )
            viewModelScope.launch {
                try {
                    repository.insertFavorite(favorite)

                } catch (e: Exception) {
                    // handler error
                }
            }
        }
    }

    fun deleteFavorite() {
        detailsLD.value?.let {
            val favorite = Favorite(
                id = it.id,
                originalTitle = it.originalTitle,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage
            )
            viewModelScope.launch {
                try {
                    repository.deleteFavorite(favorite)

                } catch (e: Exception) {
                    // handler error
                }
            }
        }
    }

}