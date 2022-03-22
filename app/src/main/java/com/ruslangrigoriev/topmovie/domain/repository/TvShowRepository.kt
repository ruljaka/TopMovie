package com.ruslangrigoriev.topmovie.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.video.Video
import com.ruslangrigoriev.topmovie.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    suspend fun getTvNow(): List<Media>
    fun getMoreTvNow(): Flow<PagingData<Media>>
    suspend fun getTvPopular(): List<Media>
    fun getMoreTvPopular(): Flow<PagingData<Media>>
    suspend fun getTvDetails(id: Int): Media?
    suspend fun getTvVideo(tv_id: Int): List<Video>
    suspend fun getTvCredits(id: Int): List<Cast>
    suspend fun searchTvPaged(query: String, page: Int): List<Media>
}