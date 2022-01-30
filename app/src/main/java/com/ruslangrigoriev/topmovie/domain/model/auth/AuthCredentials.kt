package com.ruslangrigoriev.topmovie.domain.model.auth


import com.google.gson.annotations.SerializedName

data class AuthCredentials(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("request_token")
    val requestToken: String
)