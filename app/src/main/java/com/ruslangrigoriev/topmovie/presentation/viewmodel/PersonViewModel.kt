package com.ruslangrigoriev.topmovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.person.PersonCredits
import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl
import kotlinx.coroutines.launch

class PersonViewModel(val repository: Repository) : ViewModel() {

   // private val repository = RepositoryImpl

    private val _personLD = MutableLiveData<Person>()
    val personLD: MutableLiveData<Person> get() = _personLD
    private val _personCastLD = MutableLiveData<PersonCredits>()
    val personCastLD: MutableLiveData<PersonCredits> get() = _personCastLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String> get() = _errorLD

    fun getPerson(person_id: Int) = viewModelScope.launch {
        Log.d("TAG", "getPerson -> Person ID: $person_id")
        repository.getPerson(person_id).let { response ->
            if (response.isSuccessful) {
                _personLD.postValue(response.body())
            } else {
                _errorLD.postValue(response.code().toString())
            }
        }
    }

    fun getPersonCredits(person_id: Int) = viewModelScope.launch {
        Log.d("TAG", "getPersonCredits -> Person ID: $person_id")
        repository.getPersonCredits(person_id).let { response ->
            if (response.isSuccessful) {
                _personCastLD.postValue(response.body())
            } else {
                _errorLD.postValue(response.code().toString())
            }
        }
    }
}