package com.ruslangrigoriev.topmovie.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow


class MovieViewModel(val repository: Repository) : ViewModel() {

    private lateinit var _trendingFlowData: Flow<PagingData<Movie>>
    val trendingFlowData: Flow<PagingData<Movie>>
        get() = _trendingFlowData

    init {
        getTrending()
    }

    private fun getTrending() {
        _trendingFlowData =
            Pager(PagingConfig(pageSize = 20)) {
                MoviePagingSource(repository = repository)
            }.flow.cachedIn(viewModelScope)
    }
}
