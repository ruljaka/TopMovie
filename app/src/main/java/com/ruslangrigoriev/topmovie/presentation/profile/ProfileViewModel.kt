package com.ruslangrigoriev.topmovie.presentation.profile


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.AuthRepository
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.ContentType
import com.ruslangrigoriev.topmovie.domain.model.profile.CountLikeFavorite
import com.ruslangrigoriev.topmovie.domain.model.profile.User
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel(
    val authRepository: AuthRepository,
    val repository: Repository
) : ViewModel() {

    private val _userLD = MutableLiveData<User>()
    val userLD: LiveData<User>
        get() = _userLD

    private val _countLD = MutableLiveData<CountLikeFavorite>()
    val countLD: LiveData<CountLikeFavorite>
        get() = _countLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    fun checkIfUserIsAuthenticated(): Boolean {
        return authRepository.checkIsUserIsAuthenticated()
    }

    private val _favoriteLD = MutableLiveData<List<ContentType>>()
    val favoriteLD: LiveData<List<ContentType>>
        get() = _favoriteLD

    fun fetchUserData() {
        Log.d(TAG, "fetchUserData -> ProfileViewModel")
        viewModelScope.launch {
            //_isLoadingLiveData.postValue(true)
            try {
                val user = async { repository.getUserData() }
                user.await()?.let { user ->

                    val listRatedMoviesSize = async {
                        repository.getRatedMovies(user.id)?.totalResults ?: 0
                    }
                    val listRatedTvShowsSize = async {
                        repository.getRatedTvShows(user.id)?.totalResults ?: 0
                    }
                    val listFavoriteMovies = async {
                        repository.getFavoriteMovies(user.id)
                    }
                    val listFavoriteTvShows = async {
                        repository.getFavoriteTvShows(user.id)
                    }

                    _userLD.postValue(user)
                    _countLD.postValue(
                        CountLikeFavorite(
                            countLike = listRatedMoviesSize.await() + listRatedTvShowsSize.await(),
                            countFavorite = (listFavoriteMovies.await()?.totalResults ?: 0)
                                    + (listFavoriteTvShows.await()?.totalResults ?: 0)
                        )
                    )
                    val favoriteList = mutableListOf<ContentType>().apply {
                        addAll(listFavoriteMovies.await()?.movies as List<ContentType>)
                        addAll(listFavoriteTvShows.await()?.tvShows as List<ContentType>)
                    }
                    _favoriteLD.postValue(favoriteList)

                }
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
            }
        }
    }

}