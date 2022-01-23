package com.ruslangrigoriev.topmovie.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.auth.ApiClient
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    val repository: Repository,
) : ViewModel() {

    //private val apiClient: ApiClient

    //private val _favoriteLD = MutableLiveData<List<Favorite>>()
//    private val _favoriteLD by lazy {
//        val livedata = MutableLiveData<List<Favorite>>()
//        viewModelScope.launch {
//            repository.getFavoriteList().collectLatest { list ->
//                livedata.postValue(list)
//            }
//        }
//        return@lazy livedata
//    }
//    val favoriteLD: LiveData<List<Favorite>> get() = _favoriteLD



}