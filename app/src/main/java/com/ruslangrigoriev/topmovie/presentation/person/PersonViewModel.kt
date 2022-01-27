package com.ruslangrigoriev.topmovie.presentation.person

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCreditsResponse
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.launch
import timber.log.Timber

class PersonViewModel(val repository: Repository) : ViewModel() {

    private val _personLD = MutableLiveData<Person>()
    val personLD: LiveData<Person>
        get() = _personLD

    private val _personCastLD = MutableLiveData<PersonCreditsResponse>()
    val personCastLD: LiveData<PersonCreditsResponse>
        get() = _personCastLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData


    fun fetchData(person_id: Int) = viewModelScope.launch {
        Timber.d( "getPerson -> Person ID: $person_id")
        _isLoadingLiveData.value = true
        try {
            _personLD.postValue(repository.getPerson(person_id))
            _personCastLD.postValue(repository.getPersonCredits(person_id))
            _isLoadingLiveData.value = false
        } catch (e: Exception) {
            _errorLD.postValue(e.message)
            _isLoadingLiveData.value = false
        }
    }
}