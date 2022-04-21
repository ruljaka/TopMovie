package com.ruslangrigoriev.topmovie.presentation.profile.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.topmovie.domain.repository.AuthRepository
import com.ruslangrigoriev.topmovie.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            authRepositoryImpl.logout()
            userRepository.clearDB()
        }
    }
}