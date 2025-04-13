package com.example.tabata.presentation.screens.sequence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tabata.presentation.components.SequenceCard
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import com.example.tabata.utils.getStrings

@Composable
fun WorkoutScreen(
    navController: NavHostController,
    viewModel: SequenceViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val sequences by viewModel.sequences.collectAsState()
    val fontSize by settingsViewModel.fontSize.collectAsState()
    val locale by settingsViewModel.locale.collectAsState()
    val strings = getStrings(locale)

    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        viewModel.loadSequences()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = strings.yourSequences,
                fontSize = (fontSize * 24).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = colorScheme.onBackground
            )
        }

        items(sequences) { sequence ->
            SequenceCard(
                sequence = sequence,
                onClick = { navController.navigate("sequenceInfo/${sequence.id}") },
                fontSize = fontSize
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("createSequence") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(strings.createNew, fontSize = (fontSize * 16).sp)
            }
        }
    }
}
