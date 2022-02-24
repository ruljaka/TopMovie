package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.dto.auth.AuthCredentials
import com.ruslangrigoriev.topmovie.domain.dto.auth.RequestToken
import com.ruslangrigoriev.topmovie.domain.dto.auth.Session
import com.ruslangrigoriev.topmovie.domain.dto.auth.Token

interface AuthRepository {
    suspend fun getRequestToken(): RequestToken?
    suspend fun validateRequestToken(authCredentials: AuthCredentials): RequestToken?
    suspend fun createSession(token: Token): Session?
    fun checkIsUserIsAuthenticated(): Boolean
    fun saveSession(sessionID: String)
    fun logout()
}