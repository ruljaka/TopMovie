package com.ruslangrigoriev.topmovie.presentation.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.AuthRepository
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.model.profile.CounterLikeFavorite
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    val authRepository: AuthRepository,
    val repository: Repository
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
            val user = repository.getUserData()
            user?.let { user ->
                repository.saveUserID(user.id)
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
                val counter = CounterLikeFavorite(
                    countLike = listRatedMoviesSize.await() + listRatedTvShowsSize.await(),
                    countFavorite = (listFavoriteMovies.await()?.size ?: 0)
                            + (listFavoriteTvShows.await()?.size ?: 0)
                )
                val favoriteList = mutableListOf<Media>().apply {
                    listFavoriteMovies.await()?.let { it -> addAll(it) }
                    listFavoriteTvShows.await()?.let { it -> addAll(it) }
                }
                _viewState.postValue(
                    ResultState.Success(
                        user = user, counters = counter, favoriteList = favoriteList
                    )
                )
            }
        }
    }
}