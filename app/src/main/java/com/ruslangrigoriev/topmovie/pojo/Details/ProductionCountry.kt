package com.ruslangrigoriev.topmovie.pojo.Details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String,
    @SerializedName("name")
    @Expose
    val name: String
)