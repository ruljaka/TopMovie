package com.ruslangrigoriev.topmovie.domain.dto.person


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.ruslangrigoriev.topmovie.domain.dto.movies.Movie

data class PersonCreditsResponse(
    @SerializedName("cast")
    @Expose
    val cast: List<Movie>,
    @SerializedName("crew")
    @Expose
    val crew: List<Crew>,
    @SerializedName("id")
    @Expose
    val id: Int
)