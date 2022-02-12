package com.ruslangrigoriev.topmovie.presentation.profile.settings

import androidx.lifecycle.ViewModel
import com.ruslangrigoriev.topmovie.data.repository.AuthRepository
import com.ruslangrigoriev.topmovie.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor (val authRepositoryImpl: AuthRepository) : ViewModel() {

}