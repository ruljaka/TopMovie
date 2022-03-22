package com.ruslangrigoriev.topmovie.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.video.Video
import com.ruslangrigoriev.topmovie.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMoviesTrending(page: Int): List<Media>
    suspend fun getMoviesNow(): List<Media>
    fun getMoreMoviesNow(): Flow<PagingData<Media>>
    suspend fun getMoviesPopular(): List<Media>
    fun getMoreMoviesPopular(): Flow<PagingData<Media>>
    suspend fun getMovieDetails(id: Int): Media?
    suspend fun getMovieVideo(movie_id: Int): List<Video>
    suspend fun getMovieCredits(id: Int): List<Cast>
    suspend fun searchMoviesPaged(query: String, page: Int): List<Media>

}