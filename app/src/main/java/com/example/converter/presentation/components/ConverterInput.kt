package com.example.converter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConverterInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    isHighlighted: Boolean
) {
    val dynamicFontSize = when {
        value.length <= 5 -> 24.sp
        value.length <= 10 -> 20.sp
        value.length <= 15 -> 16.sp
        value.length <= 20 -> 14.sp
        value.length <= 25 -> 12.sp
        value.length <= 30 -> 10.sp
        else -> 8.sp // Минимальный размер, если текст слишком длинный
    }

    // Если поле выделено, используем другой цвет текста
    val textColor = if (isHighlighted) Color(0xFFFFC107) else Color.Black // Золотой цвет для выделения

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier.width(250.dp),
        textStyle = TextStyle(
            textAlign = TextAlign.End,
            fontSize = dynamicFontSize,
            fontWeight = FontWeight.Bold,
            color = textColor // Применяем цвет текста в зависимости от выделения
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        enabled = enabled,
        visualTransformation = VisualTransformation.None
    )
}

