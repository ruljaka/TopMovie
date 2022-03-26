package com.ruslangrigoriev.topmovie.data.repositoryImpl

import android.content.Context
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.api.dto.auth.AuthCredentials
import com.ruslangrigoriev.topmovie.data.api.dto.auth.RequestToken
import com.ruslangrigoriev.topmovie.data.api.dto.auth.Session
import com.ruslangrigoriev.topmovie.data.api.dto.auth.Token
import com.ruslangrigoriev.topmovie.domain.repository.AuthRepository
import com.ruslangrigoriev.topmovie.domain.utils.extensions.getSessionID
import com.ruslangrigoriev.topmovie.domain.utils.extensions.processResult
import com.ruslangrigoriev.topmovie.domain.utils.extensions.saveSessionID

class AuthRepoImpl(
    private val appContext: Context,
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun getRequestToken(): RequestToken? {
        return apiService.createRequestToken()
            .processResult()
    }

    override suspend fun validateRequestToken(authCredentials: AuthCredentials): RequestToken? {
        return apiService.validateRequestToken(
            authCredentials = authCredentials
        ).processResult()
    }

    override suspend fun createSession(token: Token): Session? {
        return apiService.createSession(
            token = token
        ).processResult()
    }

    override fun checkIsUserIsAuthenticated(): Boolean {
        val session = appContext.getSessionID()
        return !session.isNullOrEmpty()
    }

    override fun saveSession(sessionID: String) {
        appContext.saveSessionID(sessionID)
    }

    override fun logout() {
        appContext.saveSessionID("")
    }

}