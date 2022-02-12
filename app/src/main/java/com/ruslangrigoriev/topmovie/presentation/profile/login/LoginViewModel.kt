package com.ruslangrigoriev.topmovie.presentation.profile.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.AuthRepository
import com.ruslangrigoriev.topmovie.domain.model.auth.AuthCredentials
import com.ruslangrigoriev.topmovie.domain.model.auth.Token
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authRepository: AuthRepository,
) : ViewModel() {

    private val _viewState = MutableLiveData<ResultState>()
    val viewState: LiveData<ResultState>
        get() = _viewState

    fun signIn(username: String, password: String) {
        _viewState.postValue(ResultState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var requestToken = authRepository.getRequestToken()
                Timber.d("requestToken -> ${requestToken?.success}")
                requestToken?.let { token ->
                    val credentials = AuthCredentials(username, password, token.requestToken)
                    requestToken = authRepository.validateRequestToken(credentials)
                    Timber.d("validateToken -> ${requestToken?.success}")
                    val session = authRepository.createSession(Token(token.requestToken))
                    Timber.d("session -> sessionID ${session?.sessionId} ${session?.success}")
                    session?.let {
                        authRepository.saveSession(session.sessionId)
                        _viewState.postValue(ResultState.Success(isLogged = true))
                    }
                }
            } catch (e: Throwable) {
                Timber.d("errorMessage -> ${e.message}")
                _viewState.postValue(ResultState.Failure(e.message))
            }
        }
    }
}