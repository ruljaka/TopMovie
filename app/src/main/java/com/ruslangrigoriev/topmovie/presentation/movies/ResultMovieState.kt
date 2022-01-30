package com.ruslangrigoriev.topmovie.presentation.movies

import com.ruslangrigoriev.topmovie.domain.model.media.Media

sealed class ResultMovieState {
    object Loading : ResultMovieState()
    class Failure(val errorMessage: String?) : ResultMovieState()
    class Success(
        val nowList: List<Media>,
        val popularList: List<Media>
    ) : ResultMovieState()
}