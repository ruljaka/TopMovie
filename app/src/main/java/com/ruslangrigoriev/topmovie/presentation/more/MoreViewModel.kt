package com.ruslangrigoriev.topmovie.presentation.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ruslangrigoriev.topmovie.domain.repository.MovieRepository
import com.ruslangrigoriev.topmovie.domain.repository.TvShowRepository
import com.ruslangrigoriev.topmovie.domain.utils.MediaType
import com.ruslangrigoriev.topmovie.domain.utils.MediaType.MOVIE
import com.ruslangrigoriev.topmovie.domain.utils.MediaType.TV
import com.ruslangrigoriev.topmovie.domain.utils.MoreType
import com.ruslangrigoriev.topmovie.domain.utils.MoreType.TOP
import com.ruslangrigoriev.topmovie.domain.utils.MoreType.POPULAR
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository,
) : ViewModel() {

    var moreType: MoreType = POPULAR
    var mediaType: MediaType = MOVIE

    fun moreMediaList() =  when (moreType) {
        TOP -> {
            when (mediaType) {
                MOVIE -> {
                    movieRepository.getMoreMoviesTop()
                        .cachedIn(viewModelScope)
                }
                TV -> {
                    tvShowRepository.getMoreTvTop()
                        .cachedIn(viewModelScope)
                }
            }
        }
        POPULAR -> {
            when (mediaType) {
                MOVIE -> {
                    movieRepository.getMoreMoviesPopular()
                        .cachedIn(viewModelScope)
                }
                TV -> {
                    tvShowRepository.getMoreTvPopular()
                        .cachedIn(viewModelScope)
                }
            }
        }
    }

}