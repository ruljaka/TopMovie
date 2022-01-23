package com.ruslangrigoriev.topmovie.domain.model.tv


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class TvResponse(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("results")
    @Expose
    val tvShows: List<TvShow>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int
)