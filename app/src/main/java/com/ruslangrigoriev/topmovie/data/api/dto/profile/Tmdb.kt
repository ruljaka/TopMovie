package com.ruslangrigoriev.topmovie.data.api.dto.profile


import com.google.gson.annotations.SerializedName

data class Tmdb(
    @SerializedName("avatar_path")
    val avatarPath: String?
)