package com.ruslangrigoriev.topmovie.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.video.Video
import com.ruslangrigoriev.topmovie.data.paging.MoviePagingSource
import com.ruslangrigoriev.topmovie.data.paging.TvPagingSource
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.repository.TvShowRepository
import com.ruslangrigoriev.topmovie.domain.utils.MoreType
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.mapTvShowToMedia
import com.ruslangrigoriev.topmovie.domain.utils.mappers.TvMapper
import kotlinx.coroutines.flow.Flow
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

    override fun getMoreTvNow(): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { TvPagingSource(apiService, MoreType.NOW) }
        ).flow
    }

    override suspend fun getTvPopular(): List<Media> {
        val response = apiService.getTvPopular()
        val popularList = getResultOrError(response)?.tvShows
        return mapTvShowToMedia(popularList)
    }

    override fun getMoreTvPopular(): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { TvPagingSource(apiService, MoreType.POPULAR) }
        ).flow
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