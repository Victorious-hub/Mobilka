package com.example.tabata.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tabata.presentation.screens.settings.SettingsViewModel

@Composable
fun ColorPickerDialog(
    isDialogVisible: MutableState<Boolean>,
    selectedColor: MutableState<String>,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val fontSize by settingsViewModel.fontSize.collectAsState()

    val colors = listOf(
        "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF", "#FFFFFF", "#000000"
    )

    if (isDialogVisible.value) {
        AlertDialog(
            onDismissRequest = { isDialogVisible.value = false },
            title = {
                Text(
                    text = "Select Color",
                    fontSize = (fontSize * 20).sp
                )
            },
            text = {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(colors.chunked(4)) { colorRow ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            colorRow.forEach { colorHex ->
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color(android.graphics.Color.parseColor(colorHex)))
                                        .clickable {
                                            selectedColor.value = colorHex
                                            isDialogVisible.value = false
                                        }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { isDialogVisible.value = false }
                ) {
                    Text(
                        "Close",
                        fontSize = (fontSize * 16).sp
                    )
                }
            }
        )
    }
}
