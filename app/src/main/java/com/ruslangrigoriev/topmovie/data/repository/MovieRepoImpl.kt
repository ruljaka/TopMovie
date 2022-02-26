package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.dto.credits.Cast
import com.ruslangrigoriev.topmovie.domain.dto.video.Video
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.mapMovieToMedia
import com.ruslangrigoriev.topmovie.domain.utils.mappers.MovieMapper
import javax.inject.Inject

class MovieRepoImpl
@Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    override suspend fun getMoviesTrending(page: Int): List<Media> {
        val response = apiService.getTrending(page = page)
        val trendingList = getResultOrError(response)?.movies
        return mapMovieToMedia(trendingList)
    }

    override suspend fun getMoviesNow(): List<Media> {
        val response = apiService.getMoviesNow()
        val nowList = getResultOrError(response)?.movies
        return mapMovieToMedia(nowList)
    }

    override suspend fun getMoviesPopular(): List<Media> {
        val response = apiService.getMoviesPopular()
        val popularList = getResultOrError(response)?.movies
        return mapMovieToMedia(popularList)
    }

    override suspend fun getMovieDetails(id: Int): Media? {
        val response = apiService.getMovieDetails(id)
        return getResultOrError(response)?.let {
            MovieMapper.map(it)
        }
    }

    override suspend fun getMovieCredits(id: Int): List<Cast> {
        val response = apiService.getMovieCredits(id)
        return getResultOrError(response)?.cast ?: emptyList()
    }

    override suspend fun getMovieVideo(movie_id: Int): List<Video> {
        val response = apiService.getMovieVideos(movie_id)
        return getResultOrError(response)?.videos ?: emptyList()
    }

    override suspend fun searchMoviesPaged(query: String, page: Int): List<Media> {
        val response = apiService.searchPagedMovie(query = query, page = page)
        val searchList = getResultOrError(response)?.movies
        return mapMovieToMedia(searchList)
    }
}