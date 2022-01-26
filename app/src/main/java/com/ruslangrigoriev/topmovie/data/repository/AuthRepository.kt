package com.ruslangrigoriev.topmovie.data.repository

import android.app.Application
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.model.auth.*
import com.ruslangrigoriev.topmovie.domain.model.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.model.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.getSession
import com.ruslangrigoriev.topmovie.domain.utils.saveSession
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

    suspend fun validateRequestToken(credentials: Credentials): RequestToken? {
        return getResultOrError(
            apiService.validateRequestToken(
                credentials = credentials
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
        val session = application.applicationContext.getSession()
        if (session != null) {
            return true
        }
        return false
    }

    fun saveSession(session: Session) {
        application.applicationContext.saveSession(session)
    }

}