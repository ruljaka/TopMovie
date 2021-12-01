package com.ruslangrigoriev.topmovie.data.model.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Details(
    @SerializedName("adult")
    @Expose
    val adult: Boolean,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    @Expose
    val belongsToCollection: Any,
    @SerializedName("budget")
    @Expose
    val budget: Int,
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>,
    @SerializedName("homepage")
    @Expose
    val homepage: String,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("imdb_id")
    @Expose
    val imdbId: String,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String,
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
    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String,
    @SerializedName("revenue")
    @Expose
    val revenue: Int,
    @SerializedName("runtime")
    @Expose
    val runtime: Int,
    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("tagline")
    @Expose
    val tagline: String,
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