package com.ruslangrigoriev.topmovie.domain.utils.extensions

import com.google.gson.Gson
import com.ruslangrigoriev.topmovie.data.api.dto.StatusResponse
import retrofit2.Response

fun <T> Response<T>.processResult(): T? {
    if (this.isSuccessful) {
        return this.body()
    } else {
        try {
            val responseError = Gson().fromJson(
                this.errorBody()?.string(),
                StatusResponse::class.java
            )
            throw Throwable(responseError.statusMessage)
        } catch (e: Exception) {
            throw Throwable("Unknown error")
        }
    }
}