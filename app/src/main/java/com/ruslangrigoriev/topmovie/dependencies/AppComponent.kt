package com.ruslangrigoriev.topmovie.dependencies

import com.ruslangrigoriev.topmovie.presentation.ui.DetailsFragment
import com.ruslangrigoriev.topmovie.presentation.ui.HomeFragment
import com.ruslangrigoriev.topmovie.presentation.ui.PersonFragment
import com.ruslangrigoriev.topmovie.presentation.ui.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(homeFragment: HomeFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(personFragment: PersonFragment)
    fun inject(searchFragment: SearchFragment)

}