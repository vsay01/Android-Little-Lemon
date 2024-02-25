package com.example.littlelemon.onboarding.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.extensions.validateEmail
import com.example.littlelemon.extensions.validateFirstName
import com.example.littlelemon.extensions.validateLastName
import com.example.littlelemon.onboarding.ui.compose.RegistrationFormState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is RegistrationFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }

            is RegistrationFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }

            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val firstNameResult = state.firstName.validateFirstName()
        val lastNameResult = state.lastName.validateLastName()
        val emailResult = state.email.validateEmail()

        val hasError = listOf(
            firstNameResult,
            lastNameResult,
            emailResult,
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                emailError = emailResult.errorMessage,
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    sealed class RegistrationFormEvent {
        data class FirstNameChanged(val firstName: String) : RegistrationFormEvent()
        data class LastNameChanged(val lastName: String) : RegistrationFormEvent()

        data class EmailChanged(val email: String) : RegistrationFormEvent()

        object Submit : RegistrationFormEvent()
    }
}