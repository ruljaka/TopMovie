package com.ruslangrigoriev.topmovie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.presentation.profile.ProfileViewModel
import com.ruslangrigoriev.topmovie.presentation.movie.MovieViewModel
import com.ruslangrigoriev.topmovie.presentation.search.SearchViewModel
import com.ruslangrigoriev.topmovie.presentation.details.DetailsViewModel
import com.ruslangrigoriev.topmovie.presentation.person.PersonViewModel
import javax.inject.Inject

class MyViewModelFactory @Inject constructor(
    private var repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(PersonViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PersonViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(repository = repository) as T
            }

        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }

//    @AssistedFactory
//    interface Factory {
//        fun create(@Assisted("query") query: String = ""): MyViewModelFactory
//    }
}
