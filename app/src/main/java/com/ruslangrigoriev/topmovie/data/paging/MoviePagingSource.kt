package com.ruslangrigoriev.topmovie.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.utils.PagingType
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import timber.log.Timber

class MoviePagingSource<T : Any>(
    private val query: String = "",
    private val type: PagingType,
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
                (PagingType.MOVIE_SEARCH) -> {
                    val data =
                        (repository.getSearchMoviesPagedResult(
                            query = query, page = currentPage
                        )
                            ?.movies ?: emptyList()) as List<T>
                    responseData = mutableListOf<T>()
                    responseData.addAll(data)
                    Timber.d( " page $currentPage responsedata = " + responseData.toString())
                }
                (PagingType.TV_SEARCH) -> {
                    val data = (repository.getSearchTvPagedResult(
                        query = query, page = currentPage
                    )
                        ?.tvShows ?: emptyList()) as List<T>
                    responseData = mutableListOf<T>()
                    responseData.addAll(data)
                }
                (PagingType.MOVIE_FLOW) -> {
                    val data = (repository.getMoviesTrending(currentPage)?.movies
                        ?: emptyList()) as List<T>
                    responseData = mutableListOf<T>()
                    responseData.addAll(data)
                    Timber.d(TAG, " page $currentPage responsedata = " + responseData.toString())
                }
//                (PagingType.FAVORITE_FLOW) -> {
//                    val favoriteMovieList = (repository.getFavoriteMovies(
//                        page = currentPage,
//                        accountID = query.toInt()
//                    )?.movies
//                        ?: emptyList()) as List<T>
//                    val favoriteTvShowList = (repository.getFavoriteTvShows(
//                        page = currentPage,
//                        accountID = query.toInt()
//                    )?.tvShows
//                        ?: emptyList()) as List<T>
//                    responseData = mutableListOf<T>()
//                    responseData.addAll(favoriteMovieList)
//                    responseData.addAll(favoriteTvShowList)
//                    Log.d(TAG, " page $currentPage,  responsedata size = ${responseData.size}  = " + responseData.toString())
//                }

                else -> {
                    //TODO
                }
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