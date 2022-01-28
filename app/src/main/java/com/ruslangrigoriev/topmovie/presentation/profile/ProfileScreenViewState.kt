package com.ruslangrigoriev.topmovie.presentation.profile

import com.ruslangrigoriev.topmovie.domain.model.ContentType
import com.ruslangrigoriev.topmovie.domain.model.profile.CounterLikeFavorite
import com.ruslangrigoriev.topmovie.domain.model.profile.User

sealed class ProfileScreenViewState {
    object Loading : ProfileScreenViewState()
    class Failure(val errorMessage: String?) : ProfileScreenViewState()
    class Success(
        val user: User,
        val counters: CounterLikeFavorite,
        val favoriteList: List<ContentType>
    ) : ProfileScreenViewState()
}