package com.example.littlelemon.datastore

data class UserPreferences(
    val firstName: String,
    val lastName: String,
    val email: String
)

fun UserPreferences.isUserPreferencesEmpty() =
    firstName.isEmpty() && lastName.isEmpty() && email.isEmpty()
