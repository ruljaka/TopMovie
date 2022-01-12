package com.ruslangrigoriev.topmovie.domain.model.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("original_title")
    @Expose
    val originalTitle: String,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
)