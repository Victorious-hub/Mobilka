package com.example.converter.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.converter.BuildConfig

@Composable
fun ConverterRow(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    enabled: Boolean,
    isHighlighted: Boolean = false
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context: Context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            ConverterSelector(
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected,
                options = options
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ConverterInput(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                isHighlighted = isHighlighted
            )

            if (BuildConfig.IS_PREMIUM) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy",
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            clipboardManager.setText(AnnotatedString(value))
                            Toast.makeText(context, "Скопировано: $value", Toast.LENGTH_SHORT).show()
                        }
                        .padding(4.dp),
                    tint = Color.Black
                )
            }
        }
    }
}
