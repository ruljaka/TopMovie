package com.ruslangrigoriev.topmovie.domain.utils.extensions

import com.ruslangrigoriev.topmovie.data.api.dto.Genre
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.movies.Movie
import com.ruslangrigoriev.topmovie.data.api.dto.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.model.mappers.MovieMapper
import com.ruslangrigoriev.topmovie.domain.model.mappers.TvMapper

fun List<Genre>.getNamesFromGenre(): String {
    return this.joinToString(", ") { it.name }
}

fun List<Media>.getTopPersonCasts(): List<Media> {
    val sortedList = this.toMutableList().sortedByDescending { it.popularity }
    return if (sortedList.size > 20) {
        sortedList.subList(0, 20)
    } else {
        sortedList
    }
}

fun List<Cast>.getTopCast(): List<Cast> {
    val sortedList = this.toMutableList().sortedByDescending { it.popularity }
    return if (sortedList.size > 15) {
        sortedList.subList(0, 15)
    } else {
        sortedList
    }
}

fun List<Movie>?.mapMovieToMedia(): List<Media> {
    return this?.map {
        MovieMapper.map(it)
    } ?: emptyList()
}

fun List<TvShow>?.mapTvToMedia(): List<Media> {
    return this?.map {
        TvMapper.map(it)
    } ?: emptyList()
}