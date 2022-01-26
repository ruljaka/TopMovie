package com.ruslangrigoriev.topmovie.domain.model.profile


import com.google.gson.annotations.SerializedName

data class Gravatar(
    @SerializedName("hash")
    val hash: String
)