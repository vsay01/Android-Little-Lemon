package com.example.littlelemon.screens.profile.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.screens.onboarding.ui.compose.TopAppBarNoProfile

@Composable
fun ProfileScreen(
    navController: NavController,
    profileScreenState: ProfileScreenState
) {
    Column {
        TopAppBarNoProfile(navController)
        BodyPanel(profileScreenState)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        navController = rememberNavController(),
        profileScreenState = ProfileScreenState(
            firstName = "titi",
            lastName = "tata",
            email = "titi@gmail.com"
        )
    )
}
