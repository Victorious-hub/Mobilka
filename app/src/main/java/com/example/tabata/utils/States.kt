package com.example.tabata.utils

data class SequenceState(
    val currentStep: SequenceStep = SequenceStep.WarmUp,
    val timeLeft: Int = 0,
    val isRunning: Boolean = false,
    val currentRound: Int = 1
)
