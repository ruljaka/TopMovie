package com.ruslangrigoriev.topmovie.domain.utils.mappers

import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.data.api.dto.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.TV_TYPE

object TvMapper : Mapper<TvShow, Media> {
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
            voteCount = input.voteCount,
            mediaType = TV_TYPE
        )
    }

}