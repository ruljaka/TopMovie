package com.ruslangrigoriev.topmovie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.api.dto.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.MoreType
import com.ruslangrigoriev.topmovie.domain.utils.extensions.mapMovieToMedia
import retrofit2.Response
import timber.log.Timber

class MoviePagingSource(
    private val apiService: ApiService,
    private val type: MoreType
) : PagingSource<Int, Media>() {

    private lateinit var responseData: List<Media>

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return try {
            val currentPage = params.key ?: 1
            val response: Response<MovieResponse> = when (type) {
                MoreType.TOP -> {
                    apiService.getMoviesTop(currentPage)
                }
                MoreType.POPULAR -> {
                    apiService.getMoviesPopular(currentPage)
                }
            }
            responseData = response.body()?.movies.mapMovieToMedia()
            Timber.d(" page $currentPage responseData = $responseData")
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = if (responseData.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

}