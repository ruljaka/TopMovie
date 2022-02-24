package com.ruslangrigoriev.topmovie.domain.dto.tv


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ruslangrigoriev.topmovie.domain.model.Genre

data class TvShow(
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String?,
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String,
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>?,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("original_name")
    @Expose
    val originalName: String,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int
)