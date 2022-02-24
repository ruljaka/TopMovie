package com.ruslangrigoriev.topmovie.domain.dto.auth


import com.google.gson.annotations.SerializedName

data class Session(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("success")
    val success: Boolean
)