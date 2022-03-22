package com.ruslangrigoriev.topmovie.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.data.paging.SearchPagingSource
import com.ruslangrigoriev.topmovie.domain.repository.MovieRepository
import com.ruslangrigoriev.topmovie.domain.repository.TvShowRepository
import com.ruslangrigoriev.topmovie.domain.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    val queryFlow = MutableStateFlow("")

    fun setQuery(query: String) {
        queryFlow.value = query
    }

    @ExperimentalCoroutinesApi
    fun getSearchMoviesFlowData(type: String): Flow<PagingData<Media>> =
        queryFlow.flatMapLatest { query ->
            Pager(PagingConfig(pageSize = 20)) {
                SearchPagingSource(
                    query = query,
                    type = type,
                    movieRepository = movieRepository,
                    tvShowRepository = tvShowRepository
                )
            }.flow.cachedIn(viewModelScope)
        }

//    @ExperimentalCoroutinesApi
//    val searchTvFlowData = queryFlow.flatMapLatest { query ->
//        Pager(PagingConfig(pageSize = 20)) {
//            MoviePagingSource(query = query, type = TV_SEARCH, repository = repository)
//        }.flow.cachedIn(viewModelScope)
//    }
}