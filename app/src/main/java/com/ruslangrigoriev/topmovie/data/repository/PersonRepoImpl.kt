package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.domain.dto.person.Person
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.getResultOrError
import com.ruslangrigoriev.topmovie.domain.utils.mapMovieToMedia
import javax.inject.Inject

class PersonRepoImpl
@Inject
constructor(
    private val apiService: ApiService,
) : PersonRepository {

    override suspend fun getPerson(person_id: Int): Person? {
        val response = apiService.getPerson(person_id)
        return getResultOrError(response)
    }

    override suspend fun getPersonCredits(person_id: Int): List<Media> {
        val response = apiService.getPersonCredits(person_id)
        val movieCastList = getResultOrError(response)?.cast
        return mapMovieToMedia(movieCastList)
    }
}