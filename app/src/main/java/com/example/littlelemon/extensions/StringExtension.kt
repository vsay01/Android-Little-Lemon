package com.example.littlelemon.extensions

import android.util.Patterns
import com.example.littlelemon.screens.onboarding.data.ValidationResult

fun String.validateFirstName(): ValidationResult {
    if (isBlank()) {
        return ValidationResult(
            successful = false,
            errorMessage = "The firstName can't be blank"
        )
    }
    return ValidationResult(
        successful = true
    )
}

fun String.validateLastName(): ValidationResult {
    if (isBlank()) {
        return ValidationResult(
            successful = false,
            errorMessage = "The lastName can't be blank"
        )
    }
    return ValidationResult(
        successful = true
    )
}

fun String.validateEmail(): ValidationResult {
    if (isBlank()) {
        return ValidationResult(
            successful = false,
            errorMessage = "The email can't be blank"
        )
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
        return ValidationResult(
            successful = false,
            errorMessage = "That's not a valid email"
        )
    }
    return ValidationResult(
        successful = true
    )
}