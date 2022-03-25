package com.ruslangrigoriev.topmovie.presentation.profile.settings

import androidx.lifecycle.ViewModel
import com.ruslangrigoriev.topmovie.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor (val authRepositoryImpl: AuthRepository) : ViewModel()