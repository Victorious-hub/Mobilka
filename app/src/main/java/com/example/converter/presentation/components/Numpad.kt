package com.example.converter.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Numpad(
    onNumberClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    isLandscape: Boolean = false,
) {
    val buttonModifier = Modifier
        .size(if (isLandscape) 70.dp else 80.dp)
        .padding(5.dp)
        .background(Color.Transparent, shape = CircleShape)

    val buttonStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)

    val textColor = MaterialTheme.colorScheme.onBackground

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        listOf(
            listOf("7", "8", "9"),
            listOf("4", "5", "6"),
            listOf("1", "2", "3"),
            listOf("0", ".", "AC"),
            listOf("00", "000", "⌫")
        ).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { text ->
                    if (text.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                Log.d("Numpad", "Button clicked: $text")
                                when (text) {
                                    "AC" -> onClearClick()
                                    "⌫" -> onBackspaceClick()
                                    "." -> onNumberClick(".")
                                    "00" -> onNumberClick("00")
                                    else -> onNumberClick(text)
                                }
                            },
                            modifier = buttonModifier,
                            colors = ButtonDefaults.textButtonColors(contentColor = textColor)
                        ) {
                            Text(
                                text = text,
                                style = buttonStyle,
                                color = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}


