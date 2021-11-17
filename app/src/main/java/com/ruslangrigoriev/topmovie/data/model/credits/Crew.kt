package com.ruslangrigoriev.topmovie.data.model.credits


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Crew(
    @SerializedName("adult")
    @Expose
    val adult: Boolean,
    @SerializedName("credit_id")
    @Expose
    val creditId: String,
    @SerializedName("department")
    @Expose
    val department: String,
    @SerializedName("gender")
    @Expose
    val gender: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("job")
    @Expose
    val job: String,
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("original_name")
    @Expose
    val originalName: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Double,
    @SerializedName("profile_path")
    @Expose
    val profilePath: String
)