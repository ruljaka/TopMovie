package com.ruslangrigoriev.topmovie.data.model.person


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.ruslangrigoriev.topmovie.data.model.movies.Movie

data class PersonCredits(
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