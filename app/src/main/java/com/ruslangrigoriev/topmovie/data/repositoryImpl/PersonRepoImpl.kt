package com.ruslangrigoriev.topmovie.data.repositoryImpl

import com.ruslangrigoriev.topmovie.data.api.ApiService
import com.ruslangrigoriev.topmovie.data.api.dto.person.Person
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.repository.PersonRepository
import com.ruslangrigoriev.topmovie.domain.utils.extensions.getTopPersonCasts
import com.ruslangrigoriev.topmovie.domain.utils.extensions.mapMovieToMedia
import com.ruslangrigoriev.topmovie.domain.utils.extensions.processResult
import javax.inject.Inject

class PersonRepoImpl
@Inject constructor(
    private val apiService: ApiService,
) : PersonRepository {

    override suspend fun getPerson(person_id: Int): Person? {
        val response = apiService.getPerson(person_id)
        return response.processResult()
    }

    override suspend fun getPersonCredits(person_id: Int): List<Media> {
        val response = apiService.getPersonCredits(person_id)
        val movieCastList = response.processResult()?.cast
        return movieCastList.mapMovieToMedia().getTopPersonCasts()
    }
}