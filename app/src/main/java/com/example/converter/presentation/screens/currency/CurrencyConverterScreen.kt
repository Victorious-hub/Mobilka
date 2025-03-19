package com.example.converter.presentation.screens.currency

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.converter.presentation.components.ConverterRow
import com.example.converter.presentation.components.Numpad
import androidx.compose.ui.platform.LocalConfiguration
import com.example.converter.BuildConfig
import com.example.converter.presentation.components.SwapButton

@Composable
fun CurrencyConverterScreen(
    viewModel: CurrencyViewModel = hiltViewModel(),
    innerPadding: PaddingValues
) {
    val textFieldValueUp = viewModel.textFieldValueUpState.value
    val textFieldValueDown = viewModel.textFieldValueDownState.value
    val options = listOf("USD", "CAD", "RUB", "EUR", "CZK", "BRL", "BGN")

    var selectedOption by rememberSaveable { mutableStateOf("USD") }
    var selectedOption1 by rememberSaveable { mutableStateOf("USD") }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val swapValues = {
        val tempOption = selectedOption
        val tempValue = textFieldValueUp.text

        selectedOption = selectedOption1
        selectedOption1 = tempOption

        viewModel.setTextFieldValueDown(tempValue, selectedOption1, selectedOption, true)
    }

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f).padding(end = 100.dp)
            ) {
                ConverterRow(
                    selectedOption = selectedOption1,
                    onOptionSelected = { option ->
                        selectedOption1 = option
                        viewModel.updateCurrency(selectedOption1, selectedOption)
                    },
                    value = textFieldValueUp.text,
                    onValueChange = {},
                    options = options,
                    enabled = false,
                    isHighlighted = false
                )
                if (BuildConfig.IS_PREMIUM) {
                    SwapButton(swapValues)
                }

                ConverterRow(
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                        viewModel.updateCurrency(selectedOption1, selectedOption)
                    },
                    value = textFieldValueDown.text,
                    onValueChange = { viewModel.setTextFieldValueDown(it, selectedOption1, selectedOption) },
                    options = options,
                    enabled = false,
                    isHighlighted = true
                )
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Numpad(
                    onNumberClick = { number ->
                        viewModel.setTextFieldValueDown(number, selectedOption1, selectedOption)
                    },
                    onClearClick = {
                        viewModel.setTextFieldValueDown("AC", selectedOption1, selectedOption)
                    },
                    onBackspaceClick = {
                        val currentText = textFieldValueDown.text
                        if (currentText.isNotEmpty()) {
                            viewModel.setTextFieldValueDown("⌫", selectedOption1, selectedOption)
                        } else {
                            viewModel.setTextFieldValueDown("0", selectedOption1, selectedOption)
                        }
                    },
                    isLandscape = isLandscape
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(75.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConverterRow(
                    selectedOption = selectedOption1,
                    onOptionSelected = { option ->
                        selectedOption1 = option
                        viewModel.updateCurrency(selectedOption1, selectedOption)
                    },
                    value = textFieldValueUp.text,
                    onValueChange = {},
                    options = options,
                    enabled = false,
                    isHighlighted = false
                )
                if (BuildConfig.IS_PREMIUM) {
                    SwapButton(swapValues)
                }
                ConverterRow(
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                        viewModel.updateCurrency(selectedOption1, selectedOption)
                    },
                    value = textFieldValueDown.text,
                    onValueChange = { viewModel.setTextFieldValueDown(it, selectedOption1, selectedOption) },
                    options = options,
                    enabled = false,
                    isHighlighted = true
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Numpad(
                onNumberClick = { number ->
                    viewModel.setTextFieldValueDown(number, selectedOption1, selectedOption)
                },
                onClearClick = {
                    viewModel.setTextFieldValueDown("AC", selectedOption1, selectedOption)
                },
                onBackspaceClick = {
                    val currentText = textFieldValueDown.text
                    if (currentText.isNotEmpty()) {
                        viewModel.setTextFieldValueDown("⌫", selectedOption1, selectedOption)
                    } else {
                        viewModel.setTextFieldValueDown("0", selectedOption1, selectedOption)
                    }
                },
                isLandscape = isLandscape
            )
        }
    }
}

