package com.ruslangrigoriev.topmovie.domain.model.media

import com.google.gson.annotations.SerializedName
import com.ruslangrigoriev.topmovie.domain.model.Genre

data class Media(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    val genres: List<Genre>?,
    val overview: String,
    val popularity: Double,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int
)
