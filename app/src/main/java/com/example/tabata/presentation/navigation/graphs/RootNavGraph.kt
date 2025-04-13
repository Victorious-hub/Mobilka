package com.example.tabata.presentation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavbardemo.screens.home.BottomNavigationBarScreen
import com.example.tabata.utils.GraphRoute

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = GraphRoute.ROOT,
        startDestination = GraphRoute.HOME
    ) {
        composable(
            route = GraphRoute.HOME,
        ) { BottomNavigationBarScreen() }
    }
}
