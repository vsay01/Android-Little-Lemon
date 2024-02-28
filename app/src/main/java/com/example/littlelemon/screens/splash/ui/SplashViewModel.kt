package com.example.littlelemon.screens.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.datastore.ImplDataStore
import com.example.littlelemon.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    implDataStore: ImplDataStore
) : ViewModel() {
    val profileData: StateFlow<UserPreferences> =
        implDataStore.getProfileData().map { userPreference ->
            userPreference
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserPreferences("", "", "")
        )
}
