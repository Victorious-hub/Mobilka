package com.example.tabata.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tabata.presentation.screens.settings.SettingsViewModel

@Composable
fun StepperControl(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    unit: String = "sec",
    textWidth: Int = 80,
    textSize: Float = 1f
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.width(250.dp)
    ) {
        Button(onClick = onDecrement) {
            Text("-", fontSize = (textSize * 18).sp)
        }

        Box(
            modifier = Modifier.width(textWidth.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$value $unit", fontSize = (textSize * 16).sp)
        }

        Button(onClick = onIncrement) {
            Text("+", fontSize = (textSize * 18).sp)
        }
    }
}

