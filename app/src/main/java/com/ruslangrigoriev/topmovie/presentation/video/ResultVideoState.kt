package com.ruslangrigoriev.topmovie.presentation.video

import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.video.Video

sealed class ResultVideoState {
    object Loading : ResultVideoState()
    class Failure(val errorMessage: String?) : ResultVideoState()
    class Success(
        val listVideo: List<Video>,
    ) : ResultVideoState()
}