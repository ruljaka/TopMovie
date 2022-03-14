package com.ruslangrigoriev.topmovie.data.api.dto.profile


import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar")
    val gravatar: Gravatar,
    @SerializedName("tmdb")
    val tmdb: Tmdb
)