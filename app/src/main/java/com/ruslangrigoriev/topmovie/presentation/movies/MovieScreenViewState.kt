package com.ruslangrigoriev.topmovie.presentation.movies

import com.ruslangrigoriev.topmovie.domain.model.movies.Movie

sealed class MovieScreenViewState {
    object Loading : MovieScreenViewState()
    class Failure(val errorMessage: String?) : MovieScreenViewState()
    class Success(
        val nowList: List<Movie>?,
        val popularList: List<Movie>?
    ) : MovieScreenViewState()
}