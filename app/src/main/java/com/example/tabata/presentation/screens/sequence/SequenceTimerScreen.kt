package com.example.tabata.presentation.screens.sequence

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tabata.R
import com.example.tabata.data.entities.WorkoutSequence
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import com.example.tabata.utils.SequenceStep
import com.example.tabata.utils.getStrings

@Composable
fun SequenceTimerScreen(
    navController: NavHostController,
    viewModel: SequenceViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    id: String
) {
    val locale by settingsViewModel.locale.collectAsState()
    val localizedStrings = getStrings(locale)

    viewModel.getSequence(id)
    val sequence by viewModel.selectedSequence.collectAsState(initial = null)
    val timerState by viewModel.sequenceState.collectAsState()
    val fontSize by settingsViewModel.fontSize.collectAsState()

    val context = LocalContext.current
    val beepPlayed = remember { mutableSetOf<Int>() }
    var lastStep by remember { mutableStateOf<SequenceStep?>(null) }

    if (sequence != null && !timerState.isRunning) {
        if (timerState.timeLeft == 0 && timerState.currentStep == SequenceStep.WarmUp) {
            viewModel.startSequence(sequence!!)
        }
    }

    if (sequence == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LaunchedEffect(timerState.timeLeft) {
            if (timerState.timeLeft in 1..3 && timerState.isRunning && timerState.timeLeft !in beepPlayed) {
                playBeep(context)
                beepPlayed.add(timerState.timeLeft)
            }
            if (timerState.timeLeft == getStepDuration(timerState.currentStep, sequence!!).toInt()) {
                beepPlayed.clear()
            }
        }

        LaunchedEffect(timerState.currentStep) {
            if (lastStep != timerState.currentStep && timerState.isRunning) {
                beepPlayed.clear()
            }
            lastStep = timerState.currentStep
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = sequence!!.title,
                fontSize = (fontSize * 24).sp
            )

            Text(
                text = "${localizedStrings.step}: ${timerState.currentStep.name}",
                fontSize = (fontSize * 20).sp
            )

            Text(
                text = "${localizedStrings.round} ${timerState.currentRound}/${sequence!!.rounds}",
                fontSize = (fontSize * 16).sp
            )

            CircularProgressIndicator(
                progress = {
                    timerState.timeLeft.toFloat() / getStepDuration(timerState.currentStep, sequence!!)
                },
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp),
                strokeWidth = 12.dp
            )

            Text(
                text = "${timerState.timeLeft} sec",
                fontSize = (fontSize * 36).sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Back",
                        modifier = Modifier.size((fontSize * 28).dp)
                    )
                }

                IconButton(onClick = { viewModel.moveToPreviousStep(sequence!!) }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Previous",
                        modifier = Modifier.size((fontSize * 28).dp)
                    )
                }

                if (timerState.isRunning) {
                    IconButton(onClick = { viewModel.pause() }) {
                        Icon(
                            Icons.Default.Pause,
                            contentDescription = "Pause",
                            modifier = Modifier.size((fontSize * 28).dp)
                        )
                    }
                } else {
                    IconButton(onClick = { viewModel.resume(sequence!!) }) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            modifier = Modifier.size((fontSize * 28).dp)
                        )
                    }
                }

                IconButton(onClick = { viewModel.moveToNextStep(sequence!!) }) {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        modifier = Modifier.size((fontSize * 28).dp)
                    )
                }

                IconButton(onClick = { viewModel.restart(sequence!!) }) {
                    Icon(
                        Icons.Default.RestartAlt,
                        contentDescription = "Restart",
                        modifier = Modifier.size((fontSize * 28).dp)
                    )
                }
            }
        }
    }
}



fun getStepDuration(step: SequenceStep, sequence: WorkoutSequence): Float {
    return when (step) {
        SequenceStep.WarmUp -> sequence.warmUpDuration.toFloat()
        SequenceStep.Workout -> sequence.workoutDuration.toFloat()
        SequenceStep.Rest -> sequence.restDuration.toFloat()
        SequenceStep.Cooldown -> sequence.cooldownDuration.toFloat()
        else -> 1f
    }
}

fun playBeep(context: Context) {
    val player = MediaPlayer.create(context, R.raw.gilfoyle_alert)
    player.setOnCompletionListener {
        it.release()
    }
    player.start()
}
