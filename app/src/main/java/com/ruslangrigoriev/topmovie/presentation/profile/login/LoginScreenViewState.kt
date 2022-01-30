package com.ruslangrigoriev.topmovie.presentation.profile.login

sealed class LoginScreenViewState {
    object Loading : LoginScreenViewState()
    class Failure(val errorMessage: String?) : LoginScreenViewState()
    class Success(val isLogged: Boolean) : LoginScreenViewState()
}