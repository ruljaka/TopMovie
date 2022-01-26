package com.ruslangrigoriev.topmovie.domain.model.movies


import com.google.gson.annotations.SerializedName
import com.ruslangrigoriev.topmovie.domain.model.ContentType
import com.ruslangrigoriev.topmovie.domain.model.Genre

data class Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int
) : ContentType {

    override fun getType(): Int {
        return ContentType.TYPE_MOVIE
    }
}