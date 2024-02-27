package com.example.littlelemon.profile.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

data class ProfileScreenState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val onLogoutClicked: (() -> Unit)? = null,
)

@Composable
fun BodyPanel(
    profileState: ProfileScreenState
) {
    Column {
        Text(
            text = stringResource(id = R.string.onboarding_personal_information),
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
        OutlinedTextField(
            value = profileState.firstName,
            onValueChange = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "First name")
            },
            enabled = false
        )
        OutlinedTextField(
            value = profileState.lastName,
            onValueChange = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "First name")
            },
            enabled = false
        )
        OutlinedTextField(
            value = profileState.email,
            onValueChange = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "First name")
            },
            enabled = false
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = LittleLemonColor.yellow),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LittleLemonColor.yellow,
                contentColor = LittleLemonColor.yellow
            )
        ) {
            Text(
                text = "Log out",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    BodyPanel(
        ProfileScreenState(
            "titi",
            "tata",
            "titi@gmail.com"
        )
    )
}
