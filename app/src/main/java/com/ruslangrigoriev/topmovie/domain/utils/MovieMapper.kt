package com.ruslangrigoriev.topmovie.domain.utils

import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie

object MovieMapper : Mapper<Movie,Media> {
    override fun map(input: Movie): Media {
        return Media(
            id = input.id,
            title = input.title,
            originalTitle = input.originalTitle,
            posterPath = input.posterPath,
            backdropPath = input.posterPath,
            genres= input.genres,
            overview = input.overview,
            popularity = input.popularity,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount
        )
    }

}