package com.example.bottomnavbardemo.screens.home

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Text // ← правильный импорт
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tabata.presentation.navigation.screens.BottomBar
import com.example.tabata.presentation.navigation.graphs.HomeNavGraph
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import com.example.tabata.utils.getStrings

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBarScreen(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = { BottomBarScreen(navController = navController) }
    ) {
        HomeNavGraph(navController = navController)
    }
}

@Composable
fun BottomBarScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    // Подписка на изменения локали
    val locale by settingsViewModel.locale.collectAsState()
    val localizedStrings = getStrings(locale)

    val screens = listOf(
        BottomBar.Home(localizedStrings.home),
        BottomBar.Workout(localizedStrings.workout),
        BottomBar.Settings(localizedStrings.settings)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    val fontSize by settingsViewModel.fontSize.collectAsState()

    val colorScheme = MaterialTheme.colorScheme

    if (bottomBarDestination) {
        NavigationBar(
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface
        ) {
            screens.forEach { screen ->
                val selected = currentDestination?.route == screen.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title,
                            tint = if (selected) colorScheme.primary else colorScheme.onSurfaceVariant
                        )
                    },
                    label = {
                        Text(
                            text = screen.title,
                            fontSize = (fontSize * 12).sp,
                            color = if (selected) colorScheme.primary else colorScheme.onSurfaceVariant
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    }
}


