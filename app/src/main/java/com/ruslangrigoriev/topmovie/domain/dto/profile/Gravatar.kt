package com.ruslangrigoriev.topmovie.domain.dto.profile


import com.google.gson.annotations.SerializedName

data class Gravatar(
    @SerializedName("hash")
    val hash: String
)