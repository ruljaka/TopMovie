package com.ruslangrigoriev.topmovie.data.api.dto.profile


import com.google.gson.annotations.SerializedName

data class Gravatar(
    @SerializedName("hash")
    val hash: String
)