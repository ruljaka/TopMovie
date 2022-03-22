package com.ruslangrigoriev.topmovie.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.video.Video
import com.ruslangrigoriev.topmovie.data.paging.MoviePagingSource
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.repository.MovieRepository
import com.ruslangrigoriev.topmovie.domain.utils.MoreType
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.mapMovieToMedia
import com.ruslangrigoriev.topmovie.domain.utils.mappers.MovieMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepoImpl
@Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

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

    override fun getMoreMoviesNow(): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(apiService, MoreType.NOW) }
        ).flow
    }

    override suspend fun getMoviesPopular(): List<Media> {
        val response = apiService.getMoviesPopular()
        val popularList = getResultOrError(response)?.movies
        return mapMovieToMedia(popularList)
    }

    override fun getMoreMoviesPopular(): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(apiService, MoreType.POPULAR) }
        ).flow
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