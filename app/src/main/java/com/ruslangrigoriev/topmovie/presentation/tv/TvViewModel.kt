package com.ruslangrigoriev.topmovie.presentation.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ResultState.Failure(throwable.message))
    }

    fun fetchData() {
        viewModelScope.launch(exceptionHandler) {
            Timber.d("fetchData")
            _viewState.value = ResultState.Loading
            val listNow = async { repository.getTvNow() }
            val listPopular = async { repository.getTvPopular() }
            _viewState.postValue(
                ResultState.Success(
                    listNow = listNow.await(),
                    listPopular = listPopular.await()
                )
            )
        }
    }
}