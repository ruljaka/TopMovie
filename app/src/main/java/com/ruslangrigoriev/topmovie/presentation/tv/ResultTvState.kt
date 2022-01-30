package com.ruslangrigoriev.topmovie.presentation.tv

import com.ruslangrigoriev.topmovie.domain.model.media.Media

sealed class ResultTvState {
    object Loading : ResultTvState()
    class Failure(val errorMessage: String?) : ResultTvState()
    class Success(
        val nowList: List<Media>,
        val popularList: List<Media>
    ) : ResultTvState()
}