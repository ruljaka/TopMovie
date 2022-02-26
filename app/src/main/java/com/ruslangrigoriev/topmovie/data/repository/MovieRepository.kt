package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.dto.credits.Cast
import com.ruslangrigoriev.topmovie.domain.dto.video.Video
import com.ruslangrigoriev.topmovie.domain.model.media.Media

interface MovieRepository {
    suspend fun getMoviesTrending(page: Int): List<Media>
    suspend fun getMoviesNow(): List<Media>
    suspend fun getMoviesPopular(): List<Media>
    suspend fun getMovieDetails(id: Int): Media?
    suspend fun getMovieVideo(movie_id: Int): List<Video>
    suspend fun getMovieCredits(id: Int): List<Cast>
    suspend fun searchMoviesPaged(query: String, page: Int): List<Media>
}