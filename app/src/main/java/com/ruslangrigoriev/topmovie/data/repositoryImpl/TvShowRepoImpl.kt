package com.ruslangrigoriev.topmovie.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.video.Video
import com.ruslangrigoriev.topmovie.data.paging.TvPagingSource
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.model.mappers.TvMapper
import com.ruslangrigoriev.topmovie.domain.repository.TvShowRepository
import com.ruslangrigoriev.topmovie.domain.utils.MoreType
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.mapTvShowToMedia
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvShowRepoImpl
@Inject constructor(
    private val apiService: ApiService,
) : TvShowRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getTvNow(): List<Media> {
        return withContext(ioDispatcher) {
            val response = apiService.getTVNow()
            val nowList = getResultOrError(response)?.tvShows
            mapTvShowToMedia(nowList)
        }
    }

    override fun getMoreTvNow(): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { TvPagingSource(apiService, MoreType.NOW) }
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun getTvPopular(): List<Media> {
        return withContext(ioDispatcher) {
            val response = apiService.getTvPopular()
            val popularList = getResultOrError(response)?.tvShows
            mapTvShowToMedia(popularList)
        }
    }

    override fun getMoreTvPopular(): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { TvPagingSource(apiService, MoreType.POPULAR) }
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun getTvDetails(id: Int): Media? {
        return withContext(ioDispatcher) {
            val response = apiService.getTvDetails(id)
            getResultOrError(response)?.let {
                TvMapper.map(it)
            }
        }
    }

    override suspend fun getTvCredits(id: Int): List<Cast> {
        return withContext(ioDispatcher) {
            val response = apiService.getTvCredits(id)
            getResultOrError(response)?.cast ?: emptyList()
        }
    }

    override suspend fun getTvVideo(tv_id: Int): List<Video> {
        return withContext(ioDispatcher) {
            val response = apiService.getTvVideos(tv_id)
            getResultOrError(response)?.videos ?: emptyList()
        }
    }

    override suspend fun searchTvPaged(query: String, page: Int): List<Media> {
        return withContext(ioDispatcher) {
            val response = apiService.searchPagedTvShow(query = query, page = page)
            val searchList = getResultOrError(response)?.tvShows
            mapTvShowToMedia(searchList)
        }
    }
}