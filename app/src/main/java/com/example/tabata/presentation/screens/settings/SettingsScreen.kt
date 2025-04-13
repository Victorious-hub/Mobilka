package com.example.tabata.presentation.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tabata.presentation.components.SettingCard
import com.example.tabata.utils.getStrings

//import com.example.tabata.Setting1Card

@SuppressLint("DefaultLocale")
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val locale by viewModel.locale.collectAsState()
    val localizedStrings = getStrings(locale)

    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val fontSize by viewModel.fontSize.collectAsState()
    val selectedLocale by viewModel.locale.collectAsState()

    var showLocaleMenu by remember { mutableStateOf(false) }

    val colorScheme = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = localizedStrings.settings,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = (fontSize * 32).sp),
                color = colorScheme.onBackground
            )
        }

        item {
            SettingCard(title = localizedStrings.application) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = localizedStrings.darkTheme,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = (fontSize * 16).sp),
                            color = colorScheme.onSurface
                        )
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { viewModel.setDarkTheme(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorScheme.onPrimary,
                                checkedTrackColor = colorScheme.primary,
                                uncheckedThumbColor = colorScheme.onSurface,
                                uncheckedTrackColor = colorScheme.surfaceVariant
                            )
                        )
                    }

                    Column {
                        Text(
                            text = "${localizedStrings.fontSize}: ${String.format("%.1f", fontSize)}x",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = (fontSize * 16).sp),
                            color = colorScheme.onSurface
                        )

                        Slider(
                            value = fontSize,
                            onValueChange = { viewModel.setFontSize(it) },
                            valueRange = 0.8f..1.5f,
                            steps = 6,
                            colors = SliderDefaults.colors(
                                thumbColor = colorScheme.primary,
                                activeTrackColor = colorScheme.primary
                            )
                        )
                    }

                    Column {
                        Text(
                            text = localizedStrings.language,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = (fontSize * 16).sp),
                            color = colorScheme.onSurface
                        )

                        Box(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { showLocaleMenu = true },
                                shape = MaterialTheme.shapes.small,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorScheme.primaryContainer
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    selectedLocale.getDisplayName(selectedLocale).uppercase(),
                                    color = colorScheme.onPrimaryContainer,
                                    fontSize = (fontSize * 16).sp
                                )
                            }

                            DropdownMenu(
                                expanded = showLocaleMenu,
                                onDismissRequest = { showLocaleMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text("English", fontSize = (fontSize * 16).sp)
                                    },
                                    onClick = {
                                        viewModel.setLocale("en")
                                        showLocaleMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text("Русский", fontSize = (fontSize * 16).sp)
                                    },
                                    onClick = {
                                        viewModel.setLocale("ru")
                                        showLocaleMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = { viewModel.clearAllData() },
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.error),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = localizedStrings.clearAllData,
                    color = colorScheme.onError,
                    fontSize = (fontSize * 16).sp
                )
            }
        }
    }
}

