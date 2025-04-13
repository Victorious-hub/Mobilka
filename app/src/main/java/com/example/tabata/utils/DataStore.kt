package com.example.tabata.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
    val FONT_SIZE = floatPreferencesKey("font_size")
    val LOCALE = stringPreferencesKey("locale")
}
