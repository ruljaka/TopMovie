package com.ruslangrigoriev.topmovie.domain.model.auth


import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("request_token")
    val requestToken: String
)