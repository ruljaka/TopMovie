package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.model.auth.*
import com.ruslangrigoriev.topmovie.domain.utils.*
import javax.inject.Inject

class AuthRepository(private val application: Application) {

    @Inject
    lateinit var apiService: ApiService

    init {
        application.appComponent.inject(this)
    }

    suspend fun getRequestToken(): RequestToken? {
        return getResultOrError(
            apiService.createRequestToken()
        )
    }

    suspend fun validateRequestToken(authCredentials: AuthCredentials): RequestToken? {
        return getResultOrError(
            apiService.validateRequestToken(
                authCredentials = authCredentials
            )
        )
    }

    suspend fun createSession(token: Token): Session? {
        return getResultOrError(
            apiService.createSession(
                token = token
            )
        )
    }

    fun checkIsUserIsAuthenticated(): Boolean {
        val session = application.applicationContext.getSessionID()
        if (!session.isNullOrEmpty()) {
            return true
        }
        return false
    }

    fun saveSession(sessionID: String) {
        application.applicationContext.saveSessionID(sessionID)
    }

    fun logout() {
        application.applicationContext.saveSessionID("")
    }

}