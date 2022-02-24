package com.ruslangrigoriev.topmovie.domain.dto.credits


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CreditsResponse(
    @SerializedName("cast")
    @Expose
    val cast: List<Cast>,
    @SerializedName("crew")
    @Expose
    val crew: List<Crew>,
    @SerializedName("id")
    @Expose
    val id: Int
)