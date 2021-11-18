package com.ruslangrigoriev.topmovie.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult")
    @Expose
    val adult: Boolean,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String,
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String?,
    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int>,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("media_type")
    @Expose
    val mediaType: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String>,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String,
    @SerializedName("original_name")
    @Expose
    val originalName: String,
    @SerializedName("original_title")
    @Expose
    val originalTitle: String?,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String,
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
    val voteCount: Int,
)