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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val _favoriteListLD = MutableLiveData<ContentType>()
    val favoriteListLD: LiveData<ContentType>
        get() = _favoriteListLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    fun checkIfUserIsAuthenticated(): Boolean {
        return authRepository.checkIsUserIsAuthenticated()
    }

    fun fetchUserData() {
        Log.d(TAG, "fetchUserData -> ProfileViewModel")
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingLiveData.postValue(true)
            try {
                val user =
                    withContext(Dispatchers.IO) { repository.getUserData() }
                user?.let {
                    //rated count
                    val listRatedMoviesSize = withContext(Dispatchers.IO) {
                        repository.getRatedMovies(it.id)?.totalResults ?: 0
                    }
                    val listRatedTvShowsSize = withContext(Dispatchers.IO) {
                        repository.getRatedTvShows(it.id)?.totalResults ?: 0
                    }
                    //favorite count
                    val listFavoriteMovies = withContext(Dispatchers.IO) {
                        repository.getFavoriteMovies(it.id)?.totalResults ?: 0
                    }
                    val listFavoriteTvShows = withContext(Dispatchers.IO) {
                        repository.getFavoriteTvShows(it.id)?.totalResults ?: 0
                    }




                    _countLD.postValue(
                        CountLikeFavorite(
                            countLike = listRatedMoviesSize + listRatedTvShowsSize,
                            countFavorite = listFavoriteMovies + listFavoriteTvShows
                        )
                    )
                    _userLD.postValue(it)
                }
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
            }
        }
    }


}