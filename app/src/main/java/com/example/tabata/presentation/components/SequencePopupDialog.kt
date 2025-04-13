package com.example.tabata.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.tabata.utils.getStrings

@Composable
fun SequencePopupDialog(
    isDialogVisible: MutableState<Boolean>,
    titleState: MutableState<String>,
    colorState: MutableState<String>,
    isColorPickerVisible: MutableState<Boolean>,
    onSave: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val locale by settingsViewModel.locale.collectAsState()
    val localizedStrings = getStrings(locale)
    val fontSize by settingsViewModel.fontSize.collectAsState()

    if (isDialogVisible.value) {
        AlertDialog(
            onDismissRequest = { isDialogVisible.value = false },
            title = {
                Text(
                    localizedStrings.createNewSequence,
                    fontSize = (fontSize * 20).sp
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    TextField(
                        value = titleState.value,
                        onValueChange = { titleState.value = it },
                        label = {
                            Text(
                                localizedStrings.title,
                                fontSize = (fontSize * 14).sp
                            )
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = (fontSize * 16).sp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            localizedStrings.color,
                            fontSize = (fontSize * 14).sp
                        )

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = try {
                                        Color(android.graphics.Color.parseColor(colorState.value))
                                    } catch (e: IllegalArgumentException) {
                                        Color.Gray
                                    },
                                    shape = CircleShape
                                )
                                .border(1.dp, Color.Black, CircleShape)
                        )

                        Button(
                            onClick = { isColorPickerVisible.value = true },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                localizedStrings.chooseColor,
                                fontSize = (fontSize * 14).sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSave()
                        isDialogVisible.value = false
                    }
                ) {
                    Text(localizedStrings.save, fontSize = (fontSize * 14).sp)
                }
            },
            dismissButton = {
                Button(
                    onClick = { isDialogVisible.value = false }
                ) {
                    Text(localizedStrings.cancel, fontSize = (fontSize * 14).sp)
                }
            }
        )
    }
}

