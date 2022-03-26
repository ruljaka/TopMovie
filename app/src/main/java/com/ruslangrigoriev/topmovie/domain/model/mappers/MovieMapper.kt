package com.ruslangrigoriev.topmovie.domain.model.mappers

import com.ruslangrigoriev.topmovie.data.api.dto.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.extensions.formatDate
import com.ruslangrigoriev.topmovie.domain.utils.extensions.getNamesFromGenre

object MovieMapper : Mapper<Movie, Media> {
    override fun map(input: Movie): Media {
        return Media(
            id = input.id,
            title = input.title,
            originalTitle = input.originalTitle,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            genres = input.genres?.getNamesFromGenre(),
            overview = input.overview,
            popularity = input.popularity,
            releaseDate = input.releaseDate?.formatDate(),
            voteAverage = input.voteAverage,
            voteCount = input.voteCount.toString(),
            mediaType = MOVIE_TYPE
        )
    }
}