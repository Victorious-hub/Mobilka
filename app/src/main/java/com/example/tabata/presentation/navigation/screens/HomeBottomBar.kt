package com.example.tabata.presentation.navigation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tabata.utils.LocalizedStrings
import com.example.tabata.utils.getStrings
import java.util.Locale

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data class Home(val localizedTitle: String) : BottomBar(
        route = "HOME",
        title = localizedTitle,
        icon = Icons.Default.Home
    )

    data class Workout(val localizedTitle: String) : BottomBar(
        route = "WORKOUT",
        title = localizedTitle,
        icon = Icons.Default.Timer
    )

    data class Settings(val localizedTitle: String) : BottomBar(
        route = "SETTINGS",
        title = localizedTitle,
        icon = Icons.Default.Settings
    )

    companion object {
        fun getLocalizedStrings(locale: Locale): LocalizedStrings {
            return getStrings(locale)
        }
    }
}
