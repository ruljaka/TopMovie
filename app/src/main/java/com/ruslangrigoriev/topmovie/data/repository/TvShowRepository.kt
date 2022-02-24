package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.dto.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.media.Media

interface TvShowRepository {
    suspend fun getTvNow(): List<Media>
    suspend fun getTvPopular(): List<Media>
    suspend fun getTvDetails(id: Int): Media?
    suspend fun getTvCredits(id: Int): List<Cast>
    suspend fun searchTvPaged(query: String, page: Int): List<Media>
}