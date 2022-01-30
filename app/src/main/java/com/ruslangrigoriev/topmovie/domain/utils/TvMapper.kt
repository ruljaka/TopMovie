package com.ruslangrigoriev.topmovie.domain.utils

import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow

object TvMapper : Mapper<TvShow,Media> {
    override fun map(input: TvShow): Media {
        return Media(
            id = input.id,
            title = input.name,
            originalTitle = input.originalName,
            posterPath = input.posterPath,
            backdropPath = input.posterPath,
            genres= input.genres,
            overview = input.overview,
            popularity = input.popularity,
            releaseDate = input.firstAirDate,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount
        )
    }

}