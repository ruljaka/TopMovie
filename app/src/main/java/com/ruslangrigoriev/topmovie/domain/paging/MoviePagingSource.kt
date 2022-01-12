package com.ruslangrigoriev.topmovie.domain.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.utils.TAG

class MoviePagingSource(
    private val query: String = "",
    private val repository: Repository,
) : PagingSource<Int, Movie>() {

    private lateinit var responseData: MutableList<Movie>

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            if (query.isEmpty()) {
                val data = repository.getPagedTrending(currentPage)?.movies ?: emptyList()
                responseData = mutableListOf<Movie>()
                responseData.addAll(data)
            } else {
                val data = repository.getSearchPagedResult(query = query, page = currentPage)
                    ?.movies ?: emptyList()
                responseData = mutableListOf<Movie>()
                responseData.addAll(data)
            }

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            //TODO обработать ошибку
            //e.message?.let { Log.d(TAG, it) }
            LoadResult.Error(e)
        }
    }
}