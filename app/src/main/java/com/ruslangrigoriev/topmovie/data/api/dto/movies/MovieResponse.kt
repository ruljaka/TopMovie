package com.ruslangrigoriev.topmovie.data.api.dto.movies


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieResponse(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("results")
    @Expose
    val movies: List<Movie>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int
)