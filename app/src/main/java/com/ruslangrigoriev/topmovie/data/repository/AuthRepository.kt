package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.model.auth.AuthCredentials
import com.ruslangrigoriev.topmovie.domain.model.auth.RequestToken
import com.ruslangrigoriev.topmovie.domain.model.auth.Session
import com.ruslangrigoriev.topmovie.domain.model.auth.Token

interface AuthRepository {
    suspend fun getRequestToken(): RequestToken?
    suspend fun validateRequestToken(authCredentials: AuthCredentials): RequestToken?
    suspend fun createSession(token: Token): Session?
    fun checkIsUserIsAuthenticated(): Boolean
    fun saveSession(sessionID: String)
    fun logout()
}