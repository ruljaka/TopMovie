package com.ruslangrigoriev.topmovie.presentation.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.AuthRepository
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.ContentType
import com.ruslangrigoriev.topmovie.domain.model.profile.CounterLikeFavorite
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    val authRepository: AuthRepository,
    val repository: Repository
) : ViewModel() {

    private val _viewState = MutableLiveData<ProfileScreenViewState>()
    val viewState: LiveData<ProfileScreenViewState>
        get() = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(ProfileScreenViewState.Failure(throwable.message))
    }

    fun checkIfUserIsAuthenticated(): Boolean {
        return authRepository.checkIsUserIsAuthenticated()
    }

    fun fetchUserData() {
        Timber.d("fetchUserData")
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ProfileScreenViewState.Loading
            val user = repository.getUserData()
            user?.let {
                repository.saveUserID(it.id)
                val listRatedMoviesSize = async {
                    repository.getRatedMovies(it.id)?.totalResults ?: 0
                }
                val listRatedTvShowsSize = async {
                    repository.getRatedTvShows(it.id)?.totalResults ?: 0
                }
                val listFavoriteMovies = async {
                    repository.getFavoriteMovies(it.id)
                }
                val listFavoriteTvShows = async {
                    repository.getFavoriteTvShows(it.id)
                }
                val counter = CounterLikeFavorite(
                    countLike = listRatedMoviesSize.await() + listRatedTvShowsSize.await(),
                    countFavorite = (listFavoriteMovies.await()?.totalResults ?: 0)
                            + (listFavoriteTvShows.await()?.totalResults ?: 0)
                )
                val favoriteList = mutableListOf<ContentType>().apply {
                    addAll(listFavoriteMovies.await()?.movies as List<ContentType>)
                    addAll(listFavoriteTvShows.await()?.tvShows as List<ContentType>)
                }
                _viewState.postValue(
                    ProfileScreenViewState.Success(
                        user, counter, favoriteList
                    )
                )
            }
        }
    }
}