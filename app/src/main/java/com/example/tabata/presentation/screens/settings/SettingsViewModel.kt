package com.example.tabata.presentation.screens.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabata.utils.PreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.DARK_THEME] ?: false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val fontSize: StateFlow<Float> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.FONT_SIZE] ?: 1.0f }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 1.0f)

    val locale = dataStore.data.map {
        val savedLocale = it[PreferencesKeys.LOCALE] ?: "en"
        Locale(savedLocale)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Locale.ENGLISH)

    fun setDarkTheme(enabled: Boolean) = viewModelScope.launch {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = enabled
        }
    }

    fun setFontSize(size: Float) = viewModelScope.launch {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_SIZE] = size
        }
    }

    fun setLocale(locale: String) = viewModelScope.launch {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCALE] = locale
        }
    }

    fun clearAllData() = viewModelScope.launch {
        dataStore.edit { it.clear() }
    }
}
