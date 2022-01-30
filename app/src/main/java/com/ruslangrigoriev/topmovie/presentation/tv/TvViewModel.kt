package com.ruslangrigoriev.topmovie.presentation.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class TvViewModel(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<ResultTvState>()
    val viewState: LiveData<ResultTvState>
        get() = _viewState

    init {
        if (_viewState.value == null) {
            fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            Timber.d("fetchData")
            _viewState.value = ResultTvState.Loading
            try {
                val listNow = async { repository.getTvNow() }
                val listPopular = async { repository.getTvPopular() }
                _viewState.postValue(
                    ResultTvState.Success(
                        listNow.await(),
                        listPopular.await()
                    )
                )
            } catch (e: Throwable) {
                _viewState.postValue(ResultTvState.Failure(e.message))
            }
        }
    }
}