package com.ruslangrigoriev.topmovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ruslangrigoriev.topmovie.data.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MyViewModelFactory @AssistedInject constructor(
    @Assisted("query") private val query: String = "",
    private var repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(query = query, repository = repository) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository = repository) as T
            }
            modelClass.isAssignableFrom(PersonViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PersonViewModel(repository = repository) as T
            }
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("query") query: String = ""): MyViewModelFactory
    }
}
