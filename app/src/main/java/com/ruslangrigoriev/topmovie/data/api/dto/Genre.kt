package com.ruslangrigoriev.topmovie.data.api.dto


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Genre(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String
)