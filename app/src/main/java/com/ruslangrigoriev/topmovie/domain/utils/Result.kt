package com.ruslangrigoriev.topmovie.domain.utils

/**
 * Generic class for holding success response, error message and loading status
 */
data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String): Result<T> {
            return Result(Status.ERROR, null, message)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, message=$message)"
    }
}