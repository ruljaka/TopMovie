package com.ruslangrigoriev.topmovie.data.repository

import android.content.Context
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.model.auth.*
import com.ruslangrigoriev.topmovie.domain.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRepositoryImpl(
    private val appContext: Context,
    private val apiService: ApiService
) : AuthRepository {

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
        val session = appContext.getSessionID()

        if (!session.isNullOrEmpty()) {
            return true
        }
        return false
    }

    override fun saveSession(sessionID: String) {
        appContext.saveSessionID(sessionID)
    }

    override fun logout() {
        appContext.saveSessionID("")
    }

}