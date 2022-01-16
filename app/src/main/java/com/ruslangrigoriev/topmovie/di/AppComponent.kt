package com.ruslangrigoriev.topmovie.di

import com.ruslangrigoriev.topmovie.data.repository.RepositoryImpl
import com.ruslangrigoriev.topmovie.presentation.details.DetailsFragment
import com.ruslangrigoriev.topmovie.presentation.movies.MoviesFragment
import com.ruslangrigoriev.topmovie.presentation.person.PersonFragment
import com.ruslangrigoriev.topmovie.presentation.profile.ProfileFragment
import com.ruslangrigoriev.topmovie.presentation.search.SearchFragment
import com.ruslangrigoriev.topmovie.presentation.tv.TvFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DataBaseModule::class])
interface AppComponent {

    fun inject(moviesFragment: MoviesFragment)
    fun inject(tvFragment: TvFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(personFragment: PersonFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(profileFragment: ProfileFragment)

    fun inject(repositoryImpl: RepositoryImpl)

}