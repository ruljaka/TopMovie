package com.ruslangrigoriev.topmovie.data.api.dto.auth


import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("request_token")
    val requestToken: String
)