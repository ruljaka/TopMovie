package com.ruslangrigoriev.topmovie.domain.repository

import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.data.api.dto.person.Person

interface PersonRepository {
    suspend fun getPerson(person_id: Int): Person?
    suspend fun getPersonCredits(person_id: Int): List<Media>
}