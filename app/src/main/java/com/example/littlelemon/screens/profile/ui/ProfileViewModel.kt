package com.example.littlelemon.screens.profile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.datastore.ImplDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val implDataStore: ImplDataStore
) : ViewModel() {

    private val userInputEventChannel = Channel<UserInputEvent>()
    val userInputEvent = userInputEventChannel.receiveAsFlow()

    val profileData by mutableStateOf(implDataStore.getProfileData())

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Logout -> {
                logout()
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            implDataStore.clearDataStore()
            userInputEventChannel.send(UserInputEvent.Logout)
        }
    }

    sealed class UserInputEvent {
        data object Logout : UserInputEvent()
    }

    sealed class ProfileEvent {
        data object Logout : ProfileEvent()
    }
}