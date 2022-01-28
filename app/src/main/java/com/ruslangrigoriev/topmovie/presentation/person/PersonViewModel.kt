package com.ruslangrigoriev.topmovie.presentation.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class PersonViewModel(val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<PersonScreenViewState>()
    val viewState: LiveData<PersonScreenViewState>
        get() = _viewState

    fun fetchData(person_id: Int) = viewModelScope.launch {
        Timber.d("getPerson -> Person ID: $person_id")
        _viewState.value = PersonScreenViewState.Loading
        try {
            val person = async { repository.getPerson(person_id) }
            val personCastList = async { repository.getPersonCredits(person_id)?.cast }
            _viewState.postValue(
                PersonScreenViewState.Success(
                    person.await(),
                    personCastList.await()
                )
            )
        } catch (e: Exception) {
            _viewState.postValue(PersonScreenViewState.Failure(e.message))
        }
    }
}