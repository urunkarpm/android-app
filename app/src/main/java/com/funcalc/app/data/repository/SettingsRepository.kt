package com.funcalc.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Settings data class
 */
data class AppSettings(
    val soundEnabled: Boolean = true,
    val selectedTheme: String = "rainbow",
    val totalCalculations: Int = 0,
    val operationsUsed: Set<String> = emptySet()
)

/**
 * Repository for managing app settings using DataStore
 */
@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val SELECTED_THEME = stringPreferencesKey("selected_theme")
        val TOTAL_CALCULATIONS = intPreferencesKey("total_calculations")
        val OPERATIONS_USED = stringSetPreferencesKey("operations_used")
    }

    val settings: Flow<AppSettings> = context.dataStore.data.map { preferences ->
        AppSettings(
            soundEnabled = preferences[PreferencesKeys.SOUND_ENABLED] ?: true,
            selectedTheme = preferences[PreferencesKeys.SELECTED_THEME] ?: "rainbow",
            totalCalculations = preferences[PreferencesKeys.TOTAL_CALCULATIONS] ?: 0,
            operationsUsed = preferences[PreferencesKeys.OPERATIONS_USED] ?: emptySet()
        )
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SOUND_ENABLED] = enabled
        }
    }

    suspend fun setSelectedTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_THEME] = theme
        }
    }

    suspend fun incrementCalculationCount() {
        context.dataStore.edit { preferences ->
            val current = preferences[PreferencesKeys.TOTAL_CALCULATIONS] ?: 0
            preferences[PreferencesKeys.TOTAL_CALCULATIONS] = current + 1
        }
    }

    suspend fun addOperationUsed(operation: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[PreferencesKeys.OPERATIONS_USED] ?: emptySet()
            preferences[PreferencesKeys.OPERATIONS_USED] = current + operation
        }
    }

    suspend fun resetProgress() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOTAL_CALCULATIONS] = 0
            preferences[PreferencesKeys.OPERATIONS_USED] = emptySet()
        }
    }
}
