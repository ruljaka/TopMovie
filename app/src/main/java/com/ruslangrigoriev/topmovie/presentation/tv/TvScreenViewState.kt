package com.ruslangrigoriev.topmovie.presentation.tv

import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow

sealed class TvScreenViewState {
    object Loading : TvScreenViewState()
    class Failure(val errorMessage: String?) : TvScreenViewState()
    class Success(
        val nowList: List<TvShow>?,
        val popularList: List<TvShow>?
    ) : TvScreenViewState()
}