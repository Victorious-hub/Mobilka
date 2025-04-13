package com.example.tabata.presentation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tabata.presentation.navigation.screens.BottomBar
import com.example.tabata.presentation.screens.settings.SettingsScreen
import com.example.tabata.presentation.screens.home.UserHomeScreen
import com.example.tabata.presentation.screens.sequence.WorkoutScreen
import com.example.tabata.utils.GraphRoute
import java.util.Locale


@Composable
fun HomeNavGraph(
    navController: NavHostController,
) {
    val locale = Locale.getDefault()
    val localizedStrings = BottomBar.getLocalizedStrings(locale)

    NavHost(
        navController = navController,
        route = GraphRoute.HOME,
        startDestination = BottomBar.Home(localizedStrings.home).route
    ) {
        composable(route = BottomBar.Home(localizedStrings.home).route) {
            UserHomeScreen(
                navController,
            )
        }

        composable(route = BottomBar.Settings(localizedStrings.back).route) {
            SettingsScreen(
                navController,
            )
        }

        composable(route = BottomBar.Workout(localizedStrings.startTimer).route) {
            WorkoutScreen(
                navController,
            )
        }

        sequenceNavGraph(
            navController = navController
        )
    }
}
