package com.example.tabata.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.tabata.presentation.screens.sequence.SequenceCreateScreen
import com.example.tabata.presentation.screens.sequence.SequenceInfoScreen
import com.example.tabata.presentation.screens.sequence.SequenceTimerScreen
import com.example.tabata.presentation.screens.sequence.SequenceUpdateScreen
import com.example.tabata.utils.GraphRoute

fun NavGraphBuilder.sequenceNavGraph(navController: NavHostController) {
    navigation(
        route = GraphRoute.WORKOUT,
        startDestination = SequenceCRUD.CreateSequence.route
    ) {
        composable(
            route = SequenceCRUD.CreateSequence.route,
        ) { SequenceCreateScreen(navController) }

        composable(
            route = SequenceCRUD.GetSequence.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType})
        ) { backstackEntry ->
            SequenceInfoScreen(
                navController,
                id = backstackEntry.arguments?.getString("id") ?: "",
            )
        }

        composable(
            route = SequenceCRUD.UpdateSequence.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType})
        ) { backstackEntry ->
            SequenceUpdateScreen(
                navController,
                id = backstackEntry.arguments?.getString("id") ?: "",
            )
        }

        composable(
            route = SequenceCRUD.SequenceTimer.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType})
        ) { backstackEntry ->
            SequenceTimerScreen(
                navController,
                id = backstackEntry.arguments?.getString("id") ?: "",
            )
        }
    }
}

sealed class SequenceCRUD(val route: String) {
    data object CreateSequence: SequenceCRUD(route = "createSequence")
    data object GetSequence : SequenceCRUD(route = "sequenceInfo/{id}")
    data object UpdateSequence : SequenceCRUD(route = "sequenceUpdate/{id}")
    data object SequenceTimer: SequenceCRUD(route =  "sequenceTimer/{id}")
}
