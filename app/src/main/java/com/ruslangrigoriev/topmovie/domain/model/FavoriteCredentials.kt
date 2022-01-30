package com.ruslangrigoriev.topmovie.domain.model


import com.google.gson.annotations.SerializedName

data class FavoriteCredentials(
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("media_id")
    val mediaId: Int,
    @SerializedName("media_type")
    val mediaType: String
)