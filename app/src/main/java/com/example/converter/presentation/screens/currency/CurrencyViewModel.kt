package com.example.converter.presentation.screens.currency

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.domain.usecase.GetCurrencyRatesUseCase
import com.example.converter.utils.Api.CURRENCY_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.room.util.copy
import com.example.converter.common.TextFieldState
import java.text.DecimalFormat
import kotlin.text.format

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase
) : ViewModel() {

    private val _textFieldValueDownState = mutableStateOf(TextFieldState("0"))
    val textFieldValueDownState: State<TextFieldState> = _textFieldValueDownState

    fun setTextFieldValueDown(value: String, toCurrency: String, fromCurrency: String, isSwap: Boolean = false) {
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
            fetchCurrencyRates(toCurrency, fromCurrency)
        }
    }

    private val _textFieldValueUpState = mutableStateOf(TextFieldState("0"))
    val textFieldValueUpState: State<TextFieldState> = _textFieldValueUpState

    fun setTextFieldValueUp(value: String) {
        _textFieldValueUpState.value = textFieldValueUpState.value.copy(text = value)
    }

    fun updateCurrency(newToCurrency: String, newFromCurrency: String) {
        fetchCurrencyRates(newToCurrency, newFromCurrency)
    }

    fun fetchCurrencyRates(toCurrency: String, fromCurrency: String) {
        viewModelScope.launch {
            val response = getCurrencyRatesUseCase.execute(CURRENCY_TOKEN, toCurrency, fromCurrency)
            response.data?.toString()?.let { Log.d("Response", it) }

            val rate = when (toCurrency) {
                "USD" -> response.data?.data?.USD
                "BGN" -> response.data?.data?.BGN
                "BRL" -> response.data?.data?.BRL
                "CAD" -> response.data?.data?.CAD
                "CZK" -> response.data?.data?.CZK
                "DKK" -> response.data?.data?.DKK
                "EUR" -> response.data?.data?.EUR
                "RUB" -> response.data?.data?.RUB
                else -> null
            }

            val downValue = textFieldValueDownState.value.text

            if (rate != null && downValue.isNotEmpty()) {
                val convertedValue = rate * downValue.toDouble()
                val formattedValue = DecimalFormat("#.####").format(convertedValue)
                setTextFieldValueUp(formattedValue)
            } else {
                setTextFieldValueUp("0")
            }
        }
    }
}
