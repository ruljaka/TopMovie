package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.model.ResponseObject
import com.ruslangrigoriev.topmovie.domain.dto.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.dto.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.dto.person.Person
import com.ruslangrigoriev.topmovie.domain.dto.profile.User
import com.ruslangrigoriev.topmovie.domain.dto.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.dto.video.Video

interface PersonRepository {
    suspend fun getPerson(person_id: Int): Person?
    suspend fun getPersonCredits(person_id: Int): List<Media>
}