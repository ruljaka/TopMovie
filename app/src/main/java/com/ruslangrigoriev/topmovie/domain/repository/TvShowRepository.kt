package com.ruslangrigoriev.topmovie.domain.repository

import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.video.Video
import com.ruslangrigoriev.topmovie.domain.model.Media

interface TvShowRepository {
    suspend fun getTvNow(): List<Media>
    suspend fun getTvPopular(): List<Media>
    suspend fun getTvDetails(id: Int): Media?
    suspend fun getTvVideo(tv_id: Int): List<Video>
    suspend fun getTvCredits(id: Int): List<Cast>
    suspend fun searchTvPaged(query: String, page: Int): List<Media>
}