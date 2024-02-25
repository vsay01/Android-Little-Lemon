package com.example.littlelemon.onboarding.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OnboardingScreen(
    registrationFormState: RegistrationFormState
) {
    Column {
        TopAppBar()
        UpperPanel()
        BodyPanel(registrationFormState)
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        RegistrationFormState(
            firstName = "titi",
            firstNameError = null,
            onFirstNameValueChange = { },
            lastName = "tata",
            lastNameError = null,
            onLastNameValueChange = { },
            email = "titigmail.com",
            emailError = "email error",
            onEmailValueChange = { }
        )
    )
}
