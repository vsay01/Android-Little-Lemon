package com.example.littlelemon.profile.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.littlelemon.onboarding.ui.compose.TopAppBar

@Composable
fun ProfileScreen(
    profileScreenState: ProfileScreenState
) {
    Column {
        TopAppBar()
        BodyPanel(profileScreenState)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        profileScreenState = ProfileScreenState(
            firstName = "titi",
            lastName = "tata",
            email = "titi@gmail.com"
        )
    )
}
