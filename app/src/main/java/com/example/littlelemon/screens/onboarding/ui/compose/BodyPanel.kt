package com.example.littlelemon.screens.onboarding.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

data class RegistrationFormState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val onFirstNameValueChange: ((value: String) -> Unit)? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val onLastNameValueChange: ((value: String) -> Unit)? = null,
    val email: String = "",
    val emailError: String? = null,
    val onEmailValueChange: ((value: String) -> Unit)? = null,
    val onSubmit: (() -> Unit)? = null,
)

@Composable
fun BodyPanel(
    registrationFormState: RegistrationFormState
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
            value = registrationFormState.firstName,
            onValueChange = {
                registrationFormState.onFirstNameValueChange?.invoke(it)
            },
            isError = registrationFormState.firstNameError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "First name")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        if (registrationFormState.firstNameError != null) {
            Text(
                text = registrationFormState.firstNameError,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = registrationFormState.lastName,
            onValueChange = {
                registrationFormState.onLastNameValueChange?.invoke(it)
            },
            isError = registrationFormState.lastNameError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "Last name")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        if (registrationFormState.lastNameError != null) {
            Text(
                text = registrationFormState.lastNameError,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = registrationFormState.email,
            onValueChange = {
                registrationFormState.onEmailValueChange?.invoke(it)
            },
            isError = registrationFormState.emailError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        if (registrationFormState.emailError != null) {
            Text(
                text = registrationFormState.emailError,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                registrationFormState.onSubmit?.invoke()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = LittleLemonColor.yellow),
            colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow)
        ) {
            Text(
                text = "Register",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodyPanelPreview() {
    BodyPanel(
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
