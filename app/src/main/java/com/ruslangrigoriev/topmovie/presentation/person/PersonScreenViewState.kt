package com.ruslangrigoriev.topmovie.presentation.person

import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.person.Person

sealed class PersonScreenViewState {
    object Loading : PersonScreenViewState()
    class Failure(val errorMessage: String?) : PersonScreenViewState()
    class Success(
        val person: Person?,
        val personCastList: List<Movie>?
    ) : PersonScreenViewState()
}