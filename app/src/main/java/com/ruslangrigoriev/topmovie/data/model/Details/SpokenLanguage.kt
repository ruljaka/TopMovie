package com.ruslangrigoriev.topmovie.data.model.Details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class SpokenLanguage(
    @SerializedName("english_name")
    @Expose
    val englishName: String,
    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String,
    @SerializedName("name")
    @Expose
    val name: String
)