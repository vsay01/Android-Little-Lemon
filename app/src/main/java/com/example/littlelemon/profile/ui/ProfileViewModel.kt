package com.example.littlelemon.profile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.datastore.ImplDataStore
import com.example.littlelemon.extensions.validateEmail
import com.example.littlelemon.extensions.validateFirstName
import com.example.littlelemon.extensions.validateLastName
import com.example.littlelemon.home.data.DishRepository
import com.example.littlelemon.onboarding.ui.OnboardingViewModel
import com.example.littlelemon.onboarding.ui.compose.RegistrationFormState
import com.example.littlelemon.profile.ui.compose.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    implDataStore: ImplDataStore
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
        // clear data from data store
    }

    sealed class UserInputEvent {
        object Logout : UserInputEvent()
    }

    sealed class ProfileEvent {
        object Logout : ProfileEvent()
    }
}