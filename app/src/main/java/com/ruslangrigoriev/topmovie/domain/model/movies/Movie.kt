package com.ruslangrigoriev.topmovie.domain.model.movies


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ruslangrigoriev.topmovie.domain.model.Genre

data class Movie(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String?,
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>,
    @SerializedName("original_title")
    @Expose
    val originalTitle: String,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("video")
    @Expose
    val video: Boolean,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int
)