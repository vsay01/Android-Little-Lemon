package com.example.littlelemon.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val DataStore_NAME = "LITTLE_LEMON"

private val Context.dataStore by preferencesDataStore(name = DataStore_NAME)

class ImplDataStore(private val context: Context) {

    private object PreferencesKeys {
        val FIRST_NAME_KEY = stringPreferencesKey("FIRST_NAME")
        val LAST_NAME_KEY = stringPreferencesKey("LAST_NAME")
        val EMAIL_KEY = stringPreferencesKey("EMAIL")
    }

    suspend fun saveProfileData(
        firstName: String,
        lastName: String,
        email: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_NAME_KEY] = firstName
            preferences[PreferencesKeys.LAST_NAME_KEY] = lastName
            preferences[PreferencesKeys.EMAIL_KEY] = email
        }
    }

    fun getProfileData(): Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            UserPreferences(
                preferences[PreferencesKeys.FIRST_NAME_KEY].orEmpty(),
                preferences[PreferencesKeys.LAST_NAME_KEY].orEmpty(),
                preferences[PreferencesKeys.EMAIL_KEY].orEmpty()
            )
        }

    suspend fun clearDataStore() {
        context.dataStore.edit {
            it.clear()
        }
    }
}