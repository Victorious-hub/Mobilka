package com.example.tabata.presentation.screens.sequence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tabata.data.entities.WorkoutSequence
import com.example.tabata.presentation.components.ColorPickerDialog
import com.example.tabata.presentation.components.PhaseDurationBlock
import com.example.tabata.presentation.components.SequencePopupDialog
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import com.example.tabata.utils.getStrings

@Composable
fun SequenceCreateScreen(
    navController: NavHostController,
    viewModel: SequenceViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val warmUpDurationState = remember { mutableStateOf(60) }
    val workoutDurationState = remember { mutableStateOf(20) }
    val restDurationState = remember { mutableStateOf(10) }
    val cooldownDurationState = remember { mutableStateOf(60) }
    val roundsState = remember { mutableStateOf(3) }
    val restBetweenSetsState = remember { mutableStateOf(30) }

    val isDialogVisible = remember { mutableStateOf(false) }
    val titleState = remember { mutableStateOf("") }
    val colorState = remember { mutableStateOf("#FFFFFF") }
    val isColorPickerVisible = remember { mutableStateOf(false) }

    val locale by settingsViewModel.locale.collectAsState()
    val fontSize by settingsViewModel.fontSize.collectAsState()
    val strings = getStrings(locale)

    fun createNewSequence() {
        val newSequence = WorkoutSequence(
            id = 0,
            title = titleState.value,
            color = colorState.value,
            warmUpDuration = warmUpDurationState.value,
            workoutDuration = workoutDurationState.value,
            restDuration = restDurationState.value,
            cooldownDuration = cooldownDurationState.value,
            rounds = roundsState.value,
            restBetweenSets = restBetweenSetsState.value
        )
        viewModel.saveSequence(newSequence)
        navController.navigateUp()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = strings.createNewSequence,
                fontSize = (fontSize * 22).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            PhaseDurationBlock(strings.warmUpDuration, warmUpDurationState, fontSize=fontSize)
        }

        item {
            PhaseDurationBlock(strings.workoutDuration, workoutDurationState, fontSize=fontSize)
        }

        item {
            PhaseDurationBlock(strings.restDuration, restDurationState, fontSize=fontSize)
        }

        item {
            PhaseDurationBlock(strings.cooldownDuration, cooldownDurationState, fontSize=fontSize)
        }

        item {
            PhaseDurationBlock(strings.rounds, roundsState, unit = "x", fontSize = fontSize)
        }

        item {
            PhaseDurationBlock(strings.restBetweenSets, restBetweenSetsState, fontSize=fontSize)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Button(
                onClick = { isDialogVisible.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(strings.createSequence, fontSize = (fontSize * 16).sp)
            }
        }
    }

    ColorPickerDialog(
        isDialogVisible = isColorPickerVisible,
        selectedColor = colorState
    )

    SequencePopupDialog(
        isDialogVisible = isDialogVisible,
        titleState = titleState,
        colorState = colorState,
        isColorPickerVisible = isColorPickerVisible,
        onSave = { createNewSequence() },
        settingsViewModel = settingsViewModel
    )
}

