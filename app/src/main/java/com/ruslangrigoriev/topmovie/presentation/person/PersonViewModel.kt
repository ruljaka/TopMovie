package com.ruslangrigoriev.topmovie.presentation.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class PersonViewModel(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<PersonScreenViewState>()
    val viewState: LiveData<PersonScreenViewState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(PersonScreenViewState.Failure(throwable.message))
    }

    fun fetchData(person_id: Int) = viewModelScope.launch(exceptionHandler) {
        Timber.d("getPerson -> Person ID: $person_id")
        _viewState.value = PersonScreenViewState.Loading
        val person = async { repository.getPerson(person_id) }
        val personCastList = async { repository.getPersonCredits(person_id) }
        _viewState.postValue(
            PersonScreenViewState.Success(
                person.await(),
                personCastList.await()
            )
        )
    }
}