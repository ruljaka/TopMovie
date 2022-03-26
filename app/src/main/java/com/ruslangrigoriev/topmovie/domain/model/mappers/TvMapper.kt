package com.ruslangrigoriev.topmovie.domain.model.mappers

import com.ruslangrigoriev.topmovie.data.api.dto.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.TV_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.extensions.formatDate
import com.ruslangrigoriev.topmovie.domain.utils.extensions.getNamesFromGenre

object TvMapper : Mapper<TvShow, Media> {
    override fun map(input: TvShow): Media {
        return Media(
            id = input.id,
            title = input.name,
            originalTitle = input.originalName,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            genres = input.genres?.getNamesFromGenre(),
            overview = input.overview,
            popularity = input.popularity,
            releaseDate = input.firstAirDate.formatDate(),
            voteAverage = input.voteAverage,
            voteCount = input.voteCount.toString(),
            mediaType = TV_TYPE
        )
    }
}