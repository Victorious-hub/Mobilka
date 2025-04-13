package com.example.tabata.presentation.screens.sequence


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import com.example.tabata.utils.getStrings

@Composable
fun SequenceInfoScreen(
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

    if (sequence == null) {
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                strokeWidth = 6.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                localizedStrings.loading,
                fontSize = (fontSize * 16).sp
            )
        }
    } else {
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = sequence!!.title,
                    fontSize = (fontSize * 24).sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            items(
                listOf(
                    localizedStrings.warmUpDuration to "${sequence?.warmUpDuration ?: "N/A"} sec",
                    localizedStrings.workoutDuration to "${sequence?.workoutDuration ?: "N/A"} sec",
                    localizedStrings.restDuration to "${sequence?.restDuration ?: "N/A"} sec",
                    localizedStrings.cooldownDuration to "${sequence?.cooldownDuration ?: "N/A"} sec",
                    localizedStrings.rounds to (sequence?.rounds?.toString() ?: "N/A"),
                    localizedStrings.restBetweenSets to "${sequence?.restBetweenSets ?: "N/A"} sec"
                )
            ) { (label, value) ->
                InfoCard(label, value, fontSize)
            }

            item {
                Button(
                    onClick = {
                        sequence?.let { safeSequence ->
                            navController.navigate("sequenceUpdate/${safeSequence.id}")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(localizedStrings.updateSequence, fontSize = (fontSize * 16).sp)
                }
            }

            item {
                Button(
                    onClick = {
                        sequence?.let { safeSequence ->
                            viewModel.deleteSequence(safeSequence)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(localizedStrings.deleteSequence, fontSize = (fontSize * 16).sp)
                }
            }

            item {
                Button(
                    onClick = {
                        sequence?.let { safeSequence ->
                            navController.navigate("sequenceTimer/${safeSequence.id}")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(localizedStrings.startTimer, fontSize = (fontSize * 16).sp)
                }
            }

            item {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(localizedStrings.back, fontSize = (fontSize * 16).sp)
                }
            }
        }
    }
}



@Composable
fun InfoCard(label: String, value: String, fontSize: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .shadow(1.dp, shape = MaterialTheme.shapes.medium)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            fontSize = (fontSize * 14).sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontSize = (fontSize * 16).sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}




