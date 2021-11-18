package com.ruslangrigoriev.topmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.data.paging.MoviePagingSource
import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl

class MainViewModel : ViewModel() {

    private val repository = RepositoryImpl

    val trendingFlowData = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(repository = repository)
    }.flow.cachedIn(viewModelScope)

}