package com.ruslangrigoriev.topmovie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.topmovie.domain.repository.MovieRepository
import com.ruslangrigoriev.topmovie.domain.repository.TvShowRepository
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.TV_TYPE
import timber.log.Timber

class SearchPagingSource(
    private val query: String = "",
    private val type: String,
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : PagingSource<Int, Media>() {

    private lateinit var responseData: MutableList<Media>

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return try {
            val currentPage = params.key ?: 1
            when (type) {
                (MOVIE_TYPE) -> {
                    val data = movieRepository.searchMoviesPaged(
                        query = query, page = currentPage
                    )
                    responseData = data.toMutableList()
                    Timber.d(" page $currentPage responseData = $responseData")
                }
                (TV_TYPE) -> {
                    val data = tvShowRepository.searchTvPaged(
                        query = query, page = currentPage
                    )
                    responseData = data.toMutableList()
                }
//                (PagingType.MOVIE_FLOW) -> {
//                    val data = repository.getMoviesTrending(currentPage)
//                    responseData = data.toMutableList()
//                    Timber.d(TAG, " page $currentPage responseData = $responseData")
//                }
//                else -> {
//                    //TODO
//                }
            }
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
                //nextKey = if (responseData.size < 20) null else currentPage.plus(1)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}