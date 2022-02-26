package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.dto.credits.Cast
import com.ruslangrigoriev.topmovie.domain.dto.video.Video
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.mapTvShowToMedia
import com.ruslangrigoriev.topmovie.domain.utils.mappers.TvMapper
import javax.inject.Inject

class TvShowRepoImpl
@Inject constructor(
    private val apiService: ApiService,
) : TvShowRepository {

    override suspend fun getTvNow(): List<Media> {
        val response = apiService.getTVNow()
        val nowList = getResultOrError(response)?.tvShows
        return mapTvShowToMedia(nowList)
    }

    override suspend fun getTvPopular(): List<Media> {
        val response = apiService.getTvPopular()
        val popularList = getResultOrError(response)?.tvShows
        return mapTvShowToMedia(popularList)
    }

    override suspend fun getTvDetails(id: Int): Media? {
        val response = apiService.getTvDetails(id)
        return getResultOrError(response)?.let {
            TvMapper.map(it)
        }

    }

    override suspend fun getTvCredits(id: Int): List<Cast> {
        val response = apiService.getTvCredits(id)
        return getResultOrError(response)?.cast ?: emptyList()
    }

    override suspend fun getTvVideo(tv_id: Int): List<Video> {
        val response = apiService.getTvVideos(tv_id)
        return getResultOrError(response)?.videos ?: emptyList()
    }

    override suspend fun searchTvPaged(query: String, page: Int): List<Media> {
        val response = apiService.searchPagedTvShow(query = query, page = page)
        val searchList = getResultOrError(response)?.tvShows
        return mapTvShowToMedia(searchList)
    }
}