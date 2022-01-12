package com.ruslangrigoriev.topmovie.presentation.person

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.launch

class PersonViewModel(val repository: Repository) : ViewModel() {

    private val _personLD = MutableLiveData<Person>()
    val personLD: LiveData<Person>
        get() = _personLD

    private val _personCastLD = MutableLiveData<PersonCredits>()
    val personCastLD: LiveData<PersonCredits>
        get() = _personCastLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private var isPersonLoading: Boolean = false
    private var isPersonCastLoading: Boolean = false

    fun getPerson(person_id: Int) = viewModelScope.launch {
        Log.d(TAG, "getPerson -> Person ID: $person_id")
        _isLoadingLiveData.value = true
        isPersonLoading = true
        try {
            _personLD.postValue(repository.getPerson(person_id))
            isPersonLoading = false
            if (!isPersonCastLoading) {
                _isLoadingLiveData.value = false
            }
        } catch (e: Exception) {
            _errorLD.postValue(e.message)
            isPersonLoading = false
            if (!isPersonCastLoading) {
                _isLoadingLiveData.value = false
            }
        }
    }

    fun getPersonCredits(person_id: Int) = viewModelScope.launch {
        Log.d(TAG, "getPersonCredits -> Person ID: $person_id")
        isPersonCastLoading = true
        try {
            _personCastLD.postValue(repository.getPersonCredits(person_id))
            isPersonCastLoading = false
            if (!isPersonLoading) {
                _isLoadingLiveData.value = false
            }
        } catch (e: Exception) {
            _errorLD.postValue(e.message)
            isPersonCastLoading = false
            if (!isPersonLoading) {
                _isLoadingLiveData.value = false
            }
        }
    }
}