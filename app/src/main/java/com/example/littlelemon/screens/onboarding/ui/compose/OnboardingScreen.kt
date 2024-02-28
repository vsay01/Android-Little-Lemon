package com.example.littlelemon.screens.onboarding.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun OnboardingScreen(
    navController: NavController,
    registrationFormState: RegistrationFormState
) {
    Column {
        TopAppBarNoProfile(navController = navController)
        UpperPanel()
        BodyPanel(registrationFormState)
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        navController = rememberNavController(),
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
