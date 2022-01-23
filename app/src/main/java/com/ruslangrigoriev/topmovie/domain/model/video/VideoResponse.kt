package com.ruslangrigoriev.topmovie.domain.model.video


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class VideoResponse(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("results")
    @Expose
    val videos: List<Video>
)