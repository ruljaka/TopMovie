package com.ruslangrigoriev.topmovie.domain.model.media

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruslangrigoriev.topmovie.domain.model.Genre

@Entity

data class Media(
    @PrimaryKey
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
    val voteCount: Int,
    val mediaType: String
)
