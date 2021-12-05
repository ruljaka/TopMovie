package com.ruslangrigoriev.topmovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.domain.paging.MoviePagingSource
import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(
    repository: Repository,
    query: String,
) : ViewModel() {

    val queryFlow = MutableStateFlow(query)
    //private val repository = RepositoryImpl

    @ExperimentalCoroutinesApi
    val searchMoviesFlowData = queryFlow.flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSource(repository = repository, query = query)
        }.flow.cachedIn(viewModelScope)
    }

}