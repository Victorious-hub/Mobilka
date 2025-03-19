package com.example.converter.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.converter.presentation.screens.currency.CurrencyConverterScreen
import com.example.converter.presentation.screens.distance.DistanceConverterScreen
import com.example.converter.presentation.screens.weight.WeightConverterScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController,
        startDestination = Screens.Currency.route){
        composable(Screens.Currency.route){
            CurrencyConverterScreen(innerPadding = innerPadding)
        }
        composable(Screens.Distance.route){
            DistanceConverterScreen(innerPadding = innerPadding)
        }
        composable(Screens.Weight.route){
            WeightConverterScreen(innerPadding = innerPadding)
        }
    }
}
