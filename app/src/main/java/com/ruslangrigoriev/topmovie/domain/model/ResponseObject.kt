package com.ruslangrigoriev.topmovie.domain.model


import com.google.gson.annotations.SerializedName

data class ResponseObject(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
)