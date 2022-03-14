package com.ruslangrigoriev.topmovie.domain.utils

import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.data.api.dto.person.Person
import com.ruslangrigoriev.topmovie.data.api.dto.profile.User
import com.ruslangrigoriev.topmovie.data.api.dto.video.Video
import com.ruslangrigoriev.topmovie.domain.model.Media

sealed class ResultState {
    object Loading : ResultState()
    class Failure(val errorMessage: String?) : ResultState()
    class Success(
        val listNow: List<Media>? = null,
        val listPopular: List<Media>? = null,
        val details: Media? = null,
        val listCast: List<Cast>? = null,
        val person: Person? = null,
        val personCastList: List<Media>? = null,
        val listVideo: List<Video>? = null,
        val user: User? = null,
        val favoriteList: List<Media>? = null,
        val ratedList: List<Media>? = null,
        val isLogged: Boolean = false
    ) : ResultState()
}