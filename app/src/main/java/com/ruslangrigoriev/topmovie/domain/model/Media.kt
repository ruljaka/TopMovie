package com.ruslangrigoriev.topmovie.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruslangrigoriev.topmovie.data.api.dto.Genre

@Entity

data class Media(
    @PrimaryKey
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    val genres: String?,
    val overview: String,
    val popularity: Double,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: String,
    val mediaType: String
)
