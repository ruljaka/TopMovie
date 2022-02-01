package com.ruslangrigoriev.topmovie.domain.utils

import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.model.profile.CounterLikeFavorite
import com.ruslangrigoriev.topmovie.domain.model.profile.User
import com.ruslangrigoriev.topmovie.domain.model.video.Video

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
        val counters: CounterLikeFavorite? = null,
        val favoriteList: List<Media>? = null,
        val isLogged: Boolean = false
    ) : ResultState()
}