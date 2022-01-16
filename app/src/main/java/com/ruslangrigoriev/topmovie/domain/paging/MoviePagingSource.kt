package com.ruslangrigoriev.topmovie.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.QueryType
import com.ruslangrigoriev.topmovie.domain.model.QueryType.*

class MoviePagingSource<T : Any>(
    private val query: String = "",
    private val type: QueryType = EMPTY,
    private val repository: Repository,
) : PagingSource<Int, T>() {

    private lateinit var responseData: MutableList<T>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: 1
            when (type) {
                (MOVIE) -> {
                    val data =
                        (repository.getSearchMoviesPagedResult(query = query, page = currentPage)
                            ?.movies ?: emptyList()) as List<T>
                    responseData = mutableListOf<T>()
                    responseData.addAll(data)
                }
                (TV) -> {
                    val data = (repository.getSearchTvPagedResult(query = query, page = currentPage)
                        ?.tvShows ?: emptyList()) as List<T>
                    responseData = mutableListOf<T>()
                    responseData.addAll(data)
                }
                else -> {
                    val data = (repository.getMoviesTrending(currentPage)?.movies
                        ?: emptyList()) as List<T>
                    responseData = mutableListOf<T>()
                    responseData.addAll(data)
                }
            }


            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}