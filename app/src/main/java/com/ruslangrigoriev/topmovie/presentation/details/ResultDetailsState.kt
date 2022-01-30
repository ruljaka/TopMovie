package com.ruslangrigoriev.topmovie.presentation.details

import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow

sealed class ResultDetailsState {
    object Loading : ResultDetailsState()
    class Failure(val errorMessage: String?) : ResultDetailsState()
    class Success(
        val details: Media,
        val listCast: List<Cast>
    ) : ResultDetailsState()
}