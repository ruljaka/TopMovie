package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.model.auth.*
import com.ruslangrigoriev.topmovie.domain.utils.*
import javax.inject.Inject

class AuthRepositoryImpl(private val application: Application) : AuthRepository {

    @Inject
    lateinit var apiService: ApiService

    init {
        application.appComponent.inject(this)
    }

    override suspend fun getRequestToken(): RequestToken? {
        return getResultOrError(
            apiService.createRequestToken()
        )
    }

    override suspend fun validateRequestToken(authCredentials: AuthCredentials): RequestToken? {
        return getResultOrError(
            apiService.validateRequestToken(
                authCredentials = authCredentials
            )
        )
    }

    override suspend fun createSession(token: Token): Session? {
        return getResultOrError(
            apiService.createSession(
                token = token
            )
        )
    }

    override fun checkIsUserIsAuthenticated(): Boolean {
        val session = application.applicationContext.getSessionID()

        if (!session.isNullOrEmpty()) {
            return true
        }
        return false
    }

    override fun saveSession(sessionID: String) {
        application.applicationContext.saveSessionID(sessionID)
    }

    override fun logout() {
        application.applicationContext.saveSessionID("")
    }

}