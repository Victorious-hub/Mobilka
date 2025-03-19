package com.example.converter.presentation.screens.weight

import android.icu.text.DecimalFormat
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.converter.common.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor() : ViewModel() {
    private val _textFieldValueDownState = mutableStateOf(TextFieldState("0"))
    val textFieldValueDownState: State<TextFieldState> = _textFieldValueDownState

    fun setTextFieldValueDown(value: String, toUnit: String, fromUnit: String, isSwap: Boolean = false) {
        if (isSwap)
        {
            _textFieldValueDownState.value.text = ""
        }
        val currentText = _textFieldValueDownState.value.text
        val decimalLimit = 5

        val newText = when {
            value == "AC" -> {
                setTextFieldValueUp("0")
                "0"
            }

            value == "00" -> {
                if (currentText == "0" || currentText.isEmpty()) {
                    "0"
                } else {
                    currentText + "00"
                }
            }

            value == "000" -> {
                if (currentText == "0" || currentText.isEmpty()) {
                    "0"
                } else {
                    currentText + "000"
                }
            }

            value == "⌫" -> {
                if (currentText.isNotEmpty() && currentText != "0") {
                    val updatedText = currentText.dropLast(1)
                    if (updatedText.isEmpty()) {
                        "0"
                    } else {
                        updatedText
                    }
                } else {
                    "0"
                }
            }

            value == "." && currentText.contains(".") -> currentText

            value == "." -> {
                if (!currentText.contains(".")) {
                    currentText + "."
                } else {
                    currentText
                }
            }

            currentText.contains(".") -> {
                val parts = currentText.split(".")
                if (parts.size == 2 && parts[1].length < decimalLimit) {
                    currentText + value
                } else {
                    currentText
                }
            }

            currentText == "0" && value != "0" && value != "." -> value
            currentText == "0" && value == "0" -> "0"
            else -> currentText + value
        }

        _textFieldValueDownState.value = _textFieldValueDownState.value.copy(text = newText)

        if (value != "AC") {
            fetchWeightConversion(toUnit, fromUnit)
        }
    }

    private val _textFieldValueUpState = mutableStateOf(TextFieldState("0"))
    val textFieldValueUpState: State<TextFieldState> = _textFieldValueUpState

    fun setTextFieldValueUp(value: String) {
        _textFieldValueUpState.value = textFieldValueUpState.value.copy(text = value)
    }

    fun updateWeight(newToUnit: String, newFromUnit: String) {
        fetchWeightConversion(newToUnit, newFromUnit)
    }

    fun fetchWeightConversion(toUnit: String, fromUnit: String) {
        val rate = getConversionRate(fromUnit, toUnit)
        val downValue = textFieldValueDownState.value.text

        if (rate != null && downValue.isNotEmpty()) {
            val convertedValue = rate * downValue.toDouble()
            val formattedValue = DecimalFormat("#.####").format(convertedValue)
            setTextFieldValueUp(formattedValue)
        } else {
            setTextFieldValueUp("0")
        }
    }

    private fun getConversionRate(fromUnit: String, toUnit: String): Double? {
        if (fromUnit == toUnit) return 1.0

        return when (fromUnit to toUnit) {
            "Grams" to "Kilograms" -> 0.001
            "Kilograms" to "Grams" -> 1000.0
            "Pounds" to "Kilograms" -> 0.453592
            "Kilograms" to "Pounds" -> 2.20462
            "Ounces" to "Kilograms" -> 0.0283495
            "Kilograms" to "Ounces" -> 35.274
            "Grams" to "Pounds" -> 0.00220462
            "Pounds" to "Grams" -> 453.592
            "Grams" to "Ounces" -> 0.035274
            "Ounces" to "Grams" -> 28.3495
            "Pounds" to "Ounces" -> 16.0
            "Ounces" to "Pounds" -> 0.0625
            else -> null
        }
    }
}
