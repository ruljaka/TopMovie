package com.ruslangrigoriev.topmovie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ruslangrigoriev.topmovie.data.repository.AuthRepository
import com.ruslangrigoriev.topmovie.data.repository.Repository
import com.ruslangrigoriev.topmovie.presentation.details.DetailsViewModel
import com.ruslangrigoriev.topmovie.presentation.movies.MovieViewModel
import com.ruslangrigoriev.topmovie.presentation.person.PersonViewModel
import com.ruslangrigoriev.topmovie.presentation.profile.ProfileViewModel
import com.ruslangrigoriev.topmovie.presentation.profile.login.LoginViewModel
import com.ruslangrigoriev.topmovie.presentation.profile.settings.SettingsViewModel
import com.ruslangrigoriev.topmovie.presentation.search.SearchViewModel
import com.ruslangrigoriev.topmovie.presentation.tv.TvViewModel
import com.ruslangrigoriev.topmovie.presentation.video.VideoViewModel
import javax.inject.Inject

class MyViewModelFactory @Inject constructor(
    private var repository: Repository,
    private var authRepository: AuthRepository
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
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return TvViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(PersonViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PersonViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(
                    authRepository = authRepository,
                    repository = repository
                ) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(
                    authRepository = authRepository
                ) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(
                    authRepository = authRepository
                ) as T
            }

            modelClass.isAssignableFrom(VideoViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return VideoViewModel(repository = repository) as T
            }

        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }

//    @AssistedFactory
//    interface Factory {
//        fun create(@Assisted("query") query: String = ""): MyViewModelFactory
//    }
}
