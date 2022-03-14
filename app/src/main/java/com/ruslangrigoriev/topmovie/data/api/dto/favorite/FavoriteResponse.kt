package com.ruslangrigoriev.topmovie.data.api.dto.favorite


import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
)