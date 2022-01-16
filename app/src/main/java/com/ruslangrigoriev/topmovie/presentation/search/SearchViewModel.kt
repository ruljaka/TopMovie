package com.ruslangrigoriev.topmovie.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.model.QueryType.MOVIE
import com.ruslangrigoriev.topmovie.domain.model.QueryType.TV
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.paging.MoviePagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(repository: Repository) : ViewModel() {

    val queryFlow = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val searchMoviesFlowData = queryFlow.flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSource<Movie>(query = query, type = MOVIE, repository = repository)
        }.flow.cachedIn(viewModelScope)
    }

    @ExperimentalCoroutinesApi
    val searchTvFlowData = queryFlow.flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSource<TvShow>(query = query, type = TV, repository = repository)
        }.flow.cachedIn(viewModelScope)
    }
}