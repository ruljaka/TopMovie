package com.ruslangrigoriev.topmovie.domain.model.mappers

import com.ruslangrigoriev.topmovie.data.api.dto.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE

object MovieMapper : Mapper<Movie, Media> {
    override fun map(input: Movie): Media {
        return Media(
            id = input.id,
            title = input.title,
            originalTitle = input.originalTitle,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            genres = input.genres,
            overview = input.overview,
            popularity = input.popularity,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            mediaType = MOVIE_TYPE
        )
    }
}