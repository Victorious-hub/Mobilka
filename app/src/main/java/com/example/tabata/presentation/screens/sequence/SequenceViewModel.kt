package com.example.tabata.presentation.screens.sequence

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabata.domain.usecase.SequenceUseCase
import com.example.tabata.data.entities.WorkoutSequence
import com.example.tabata.utils.SequenceState
import com.example.tabata.utils.SequenceStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SequenceViewModel @Inject constructor(
    private val useCase: SequenceUseCase
) : ViewModel() {
    private val _sequenceState = MutableStateFlow(SequenceState())
    val sequenceState = _sequenceState.asStateFlow()

    private var timer: CountDownTimer? = null

    private val _sequences = MutableStateFlow<List<WorkoutSequence>>(emptyList())
    val sequences: StateFlow<List<WorkoutSequence>> = _sequences

    private val _selectedSequence = MutableStateFlow<WorkoutSequence?>(null)
    val selectedSequence: StateFlow<WorkoutSequence?> = _selectedSequence

    fun loadSequences() {
        viewModelScope.launch {
            _sequences.value = useCase.getAllSequences()
        }
    }

    fun saveSequence(sequence: WorkoutSequence) {
        viewModelScope.launch {
            if (sequence.id == 0) {
                useCase.insertSequence(sequence)
            } else {
                useCase.updateSequence(sequence)
            }
            loadSequences()
        }
    }

    fun deleteSequence(sequence: WorkoutSequence) {
        viewModelScope.launch {
            useCase.deleteSequence(sequence)
            loadSequences()
        }
    }

    fun updateSequence(sequence: WorkoutSequence) {
        viewModelScope.launch {
            useCase.updateSequence(sequence)
            loadSequences()
        }
    }

    fun getSequence(id: String) {
        viewModelScope.launch {
            _selectedSequence.value = useCase.getSequenceById(id)
        }
    }

    fun startSequence(sequence: WorkoutSequence) {
        _sequenceState.value = SequenceState(
            currentStep = SequenceStep.WarmUp,
            timeLeft = sequence.warmUpDuration,
            currentRound = 1,
            isRunning = true
        )
        startTimer(sequence)
    }

    private fun startTimer(sequence: WorkoutSequence) {
        val state = _sequenceState.value
        timer?.cancel()
        timer = object : CountDownTimer(state.timeLeft * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _sequenceState.value = state.copy(timeLeft = (millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                moveToNextStep(sequence)
            }
        }.start()
    }

    fun pause() {
        timer?.cancel()
        _sequenceState.update { it.copy(isRunning = false) }
    }

    fun resume(sequence: WorkoutSequence) {
        _sequenceState.update { it.copy(isRunning = true) }
        startTimer(sequence)
    }

    fun restart(sequence: WorkoutSequence) {
        startSequence(sequence)
    }

    fun moveToNextStep(sequence: WorkoutSequence) {
        val state = _sequenceState.value
        val next = when (state.currentStep) {
            SequenceStep.WarmUp -> SequenceStep.Workout
            SequenceStep.Workout -> if (state.currentRound < sequence.rounds) SequenceStep.Rest else SequenceStep.Cooldown
            SequenceStep.Rest -> {
                _sequenceState.update { it.copy(currentRound = it.currentRound + 1) }
                SequenceStep.Workout
            }
            SequenceStep.Cooldown -> SequenceStep.Finished
            SequenceStep.Finished -> SequenceStep.Finished
        }
        val duration = when (next) {
            SequenceStep.WarmUp -> sequence.warmUpDuration
            SequenceStep.Workout -> sequence.workoutDuration
            SequenceStep.Rest -> sequence.restDuration
            SequenceStep.Cooldown -> sequence.cooldownDuration
            else -> 0
        }
        _sequenceState.value = _sequenceState.value.copy(currentStep = next, timeLeft = duration)
        if (next != SequenceStep.Finished) startTimer(sequence)
    }

    fun moveToPreviousStep(sequence: WorkoutSequence) {
        val state = _sequenceState.value
        val previous = when (state.currentStep) {
            SequenceStep.WarmUp -> SequenceStep.WarmUp
            SequenceStep.Workout -> {
                if (state.currentRound > 1) {
                    _sequenceState.update { it.copy(currentRound = it.currentRound - 1) }
                    SequenceStep.Rest
                } else {
                    SequenceStep.WarmUp
                }
            }
            SequenceStep.Rest -> SequenceStep.Workout
            SequenceStep.Cooldown -> {
                SequenceStep.Workout
            }
            SequenceStep.Finished -> SequenceStep.Finished
        }

        val duration = when (previous) {
            SequenceStep.WarmUp -> sequence.warmUpDuration
            SequenceStep.Workout -> sequence.workoutDuration
            SequenceStep.Rest -> sequence.restDuration
            SequenceStep.Cooldown -> sequence.cooldownDuration
            else -> 0
        }

        _sequenceState.value = _sequenceState.value.copy(
            currentStep = previous,
            timeLeft = duration
        )

        if (previous != SequenceStep.Finished) startTimer(sequence)
    }







    private fun getStepIndex(step: SequenceStep): Int {
        return when (step) {
            SequenceStep.WarmUp -> 0
            SequenceStep.Workout -> 1
            SequenceStep.Rest -> 2
            SequenceStep.Cooldown -> 3
            else -> -1 // Если шаг не определен
        }
    }

    private fun getStepByIndex(index: Int): SequenceStep {
        return when (index) {
            0 -> SequenceStep.WarmUp
            1 -> SequenceStep.Workout
            2 -> SequenceStep.Rest
            3 -> SequenceStep.Cooldown
            else -> SequenceStep.WarmUp
        }
    }
}

