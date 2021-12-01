package com.ruslangrigoriev.topmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(
    private val query: String = "",
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(query = query) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel() as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
            modelClass.isAssignableFrom(PersonViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PersonViewModel() as T
            }
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}