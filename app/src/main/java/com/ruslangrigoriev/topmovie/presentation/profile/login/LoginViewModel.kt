package com.ruslangrigoriev.topmovie.presentation.profile.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.data.repository.AuthRepository
import com.ruslangrigoriev.topmovie.domain.model.auth.Credentials
import com.ruslangrigoriev.topmovie.domain.model.auth.Token
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    val authRepository: AuthRepository,
) : ViewModel() {

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean>
        get() = _isLogged

    fun signIn(username: String, password: String) {
        _isLoadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var requestToken = authRepository.getRequestToken()
                Log.d(TAG, "requestToken -> ${requestToken?.success}")
                requestToken?.let { token ->
                    val credentials = Credentials(username, password, token.requestToken)
                    requestToken = authRepository.validateRequestToken(credentials)
                    Log.d(TAG, "validateToken -> ${requestToken?.success}")
                    val session = authRepository.createSession(Token(token.requestToken))
                    Log.d(TAG, "session -> ${session?.success}")
                    session?.let {
                        authRepository.saveSession(it)
                        _isLogged.postValue(true)
                    }
                }
                _isLoadingLiveData.postValue(false)
            } catch (e: Exception) {
                _errorLD.postValue(e.message)
                _isLoadingLiveData.postValue(false)
            }
        }


    }
}