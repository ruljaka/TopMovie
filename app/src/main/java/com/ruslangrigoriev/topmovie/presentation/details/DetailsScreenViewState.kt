package com.ruslangrigoriev.topmovie.presentation.details

import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow

sealed class DetailsScreenViewState {
    object Loading : DetailsScreenViewState()
    class Failure(val errorMessage: String?) : DetailsScreenViewState()
    class SuccessMovie(
        val details: Movie?,
        val listCast: List<Cast>?
    ) : DetailsScreenViewState()
    class SuccessTvShow(
        val details: TvShow?,
        val listCast: List<Cast>?
    ) : DetailsScreenViewState()
}