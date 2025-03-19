package com.example.converter.presentation.screens.distance

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.common.TextFieldState
import com.example.converter.domain.usecase.GetCurrencyRatesUseCase
import com.example.converter.utils.Api.CURRENCY_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject


@HiltViewModel
class DistanceViewModel @Inject constructor(
) : ViewModel() {
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
            fetchDistanceConversion(toUnit, fromUnit)
        }
    }

    private val _textFieldValueUpState = mutableStateOf(TextFieldState("0"))
    val textFieldValueUpState: State<TextFieldState> = _textFieldValueUpState

    fun setTextFieldValueUp(value: String) {
        _textFieldValueUpState.value = textFieldValueUpState.value.copy(text = value)
        Log.d("Conversion", value)
    }

    fun updateDistance(newToUnit: String, newFromUnit: String) {
        fetchDistanceConversion(newToUnit, newFromUnit)
    }

    fun fetchDistanceConversion(toUnit: String, fromUnit: String) {
        val rate = getConversionRate(fromUnit, toUnit)
        val downValue = textFieldValueDownState.value.text
        Log.d("Update before", fromUnit)

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
            "Meters" to "Kilometers" -> 0.001
            "Kilometers" to "Meters" -> 1000.0
            "Miles" to "Kilometers" -> 1.60934
            "Kilometers" to "Miles" -> 0.621371
            "Miles" to "Meters" -> 1609.34
            "Meters" to "Miles" -> 0.000621371
            "Miles" to "Feet" -> 5280.0
            "Feet" to "Miles" -> 0.000189394
            "Miles" to "Inches" -> 63360.0
            "Inches" to "Miles" -> 0.0000157828
            "Kilometers" to "Feet" -> 3280.84
            "Feet" to "Kilometers" -> 0.0003048
            "Kilometers" to "Inches" -> 39370.1
            "Inches" to "Kilometers" -> 0.0000254
            "Kilometers" to "Yards" -> 1093.61
            "Yards" to "Kilometers" -> 0.0009144
            "Meters" to "Feet" -> 3.28084
            "Feet" to "Meters" -> 0.3048
            "Meters" to "Inches" -> 39.3701
            "Inches" to "Meters" -> 0.0254
            "Feet" to "Inches" -> 12.0
            "Inches" to "Feet" -> 0.0833333
            "Yards" to "Meters" -> 0.9144
            "Meters" to "Yards" -> 1.09361
            else -> null
        }
    }

}
