package com.example.tabata.presentation.screens.sequence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tabata.presentation.components.ColorPickerDialog
import com.example.tabata.presentation.components.PhaseDurationBlock
import com.example.tabata.presentation.components.SequencePopupDialog
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import com.example.tabata.utils.getStrings

@Composable
fun SequenceUpdateScreen(
    navController: NavHostController,
    viewModel: SequenceViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    id: String
) {
    val locale by settingsViewModel.locale.collectAsState()
    val localizedStrings = getStrings(locale)

    viewModel.getSequence(id)
    val sequence by viewModel.selectedSequence.collectAsState(initial = null)
    val fontSize by settingsViewModel.fontSize.collectAsState()

    Spacer(modifier = Modifier.height(30.dp))

    if (sequence == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(localizedStrings.loading, fontSize = (fontSize * 16).sp)
        }
    } else {
        // UI state
        val warmUpDurationState = remember { mutableStateOf(sequence!!.warmUpDuration) }
        val workoutDurationState = remember { mutableStateOf(sequence!!.workoutDuration) }
        val restDurationState = remember { mutableStateOf(sequence!!.restDuration) }
        val cooldownDurationState = remember { mutableStateOf(sequence!!.cooldownDuration) }
        val roundsState = remember { mutableStateOf(sequence!!.rounds) }
        val restBetweenSetsState = remember { mutableStateOf(sequence!!.restBetweenSets) }

        val isDialogVisible = remember { mutableStateOf(true) }
        val titleState = remember { mutableStateOf(sequence!!.title) }
        val colorState = remember { mutableStateOf(sequence!!.color) }
        val isColorPickerVisible = remember { mutableStateOf(false) }

        fun updateSequence() {
            val updated = sequence!!.copy(
                title = titleState.value,
                color = colorState.value,
                warmUpDuration = warmUpDurationState.value,
                workoutDuration = workoutDurationState.value,
                restDuration = restDurationState.value,
                cooldownDuration = cooldownDurationState.value,
                rounds = roundsState.value,
                restBetweenSets = restBetweenSetsState.value
            )
            viewModel.updateSequence(updated)
            navController.navigateUp()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = localizedStrings.updateSequence,
                fontSize = (fontSize * 24).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    PhaseDurationBlock(localizedStrings.warmUpDuration, warmUpDurationState, fontSize = fontSize)
                    PhaseDurationBlock(localizedStrings.workoutDuration, workoutDurationState, fontSize = fontSize)
                    PhaseDurationBlock(localizedStrings.restDuration, restDurationState, fontSize = fontSize)
                    PhaseDurationBlock(localizedStrings.cooldownDuration, cooldownDurationState, fontSize = fontSize)
                    PhaseDurationBlock(localizedStrings.rounds, roundsState, unit = "x", fontSize = fontSize)
                    PhaseDurationBlock(localizedStrings.restBetweenSets, restBetweenSetsState, fontSize = fontSize)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { isDialogVisible.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(localizedStrings.updateSequence, fontSize = (fontSize * 16).sp)
            }
        }

        if (isDialogVisible.value) {
            ColorPickerDialog(
                isDialogVisible = isColorPickerVisible,
                selectedColor = colorState
            )
        }

        SequencePopupDialog(
            isDialogVisible = isDialogVisible,
            titleState = titleState,
            colorState = colorState,
            isColorPickerVisible = isColorPickerVisible,
            onSave = { updateSequence() }
        )
    }
}



