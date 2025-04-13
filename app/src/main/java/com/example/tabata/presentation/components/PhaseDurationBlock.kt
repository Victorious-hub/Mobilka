package com.example.tabata.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PhaseDurationBlock(
    label: String,
    durationState: MutableState<Int>,
    unit: String = "sec",
    fontSize: Float = 1f
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = (fontSize * 16).sp
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        StepperControl(
            value = durationState.value,
            onIncrement = { durationState.value += 1 },
            onDecrement = { if (durationState.value > 0) durationState.value -= 1 },
            unit = unit,
            textSize = fontSize
        )
    }
}

