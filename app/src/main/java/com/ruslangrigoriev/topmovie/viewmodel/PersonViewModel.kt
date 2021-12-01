package com.ruslangrigoriev.topmovie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.model.person.Person
import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl
import kotlinx.coroutines.launch

class PersonViewModel: ViewModel() {

    private val repository = RepositoryImpl

    private val _personLD = MutableLiveData<Person>()
    val personLD: MutableLiveData<Person> get() = _personLD
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

}