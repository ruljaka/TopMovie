package com.ruslangrigoriev.topmovie.presentation.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.domain.repository.AuthRepository
import com.ruslangrigoriev.topmovie.domain.repository.UserRepository
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ResultState.Failure(throwable.message))
    }

    fun checkIfUserIsAuthenticated(): Boolean {
        return authRepository.checkIsUserIsAuthenticated()
    }

    fun fetchUserData() {
        Timber.d("fetchUserData")
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ResultState.Loading
            val user = userRepository.getUserData()
            user?.let {
                userRepository.saveUserID(it.id)
                val listRatedMovies = async {
                    userRepository.getRatedMovies(it.id)
                }
                val listRatedTvShows = async {
                    userRepository.getRatedTvShows(it.id)
                }
                val listFavoriteMovies = async {
                    userRepository.getFavoriteMovies(it.id)
                }
                val listFavoriteTvShows = async {
                    userRepository.getFavoriteTvShows(it.id)
                }
                val ratedList = mutableListOf<Media>().apply {
                    listRatedMovies.await()?.let { it -> addAll(it) }
                    listRatedTvShows.await()?.let { it -> addAll(it) }
                }
                val favoriteList = mutableListOf<Media>().apply {
                    listFavoriteMovies.await()?.let { it -> addAll(it) }
                    listFavoriteTvShows.await()?.let { it -> addAll(it) }
                }
                _viewState.postValue(
                    ResultState.Success(
                        user = user, favoriteList = favoriteList, ratedList = ratedList
                    )
                )
            }
        }
    }





}