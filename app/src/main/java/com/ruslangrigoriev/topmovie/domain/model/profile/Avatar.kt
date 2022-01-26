package com.ruslangrigoriev.topmovie.domain.model.profile


import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar")
    val gravatar: Gravatar,
    @SerializedName("tmdb")
    val tmdb: Tmdb
)