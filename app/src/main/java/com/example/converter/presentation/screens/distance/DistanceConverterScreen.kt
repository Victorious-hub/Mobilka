package com.example.converter.presentation.screens.distance

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.converter.BuildConfig
import com.example.converter.presentation.components.ConverterRow
import com.example.converter.presentation.components.Numpad
import com.example.converter.presentation.components.SwapButton
import com.example.converter.presentation.screens.currency.CurrencyViewModel


@Composable
fun DistanceConverterScreen(
    viewModel: DistanceViewModel = hiltViewModel(),
    innerPadding: PaddingValues
) {
    val textFieldValueUp = viewModel.textFieldValueUpState.value
    val textFieldValueDown = viewModel.textFieldValueDownState.value
    val options1 = listOf("Meters", "Kilometers", "Miles", "Feet", "Inches", "Yards")
    val options2 = listOf("Meters", "Kilometers", "Miles", "Feet", "Inches", "Yards")

    var selectedOption by rememberSaveable { mutableStateOf(options1.first()) }
    var selectedOption1 by rememberSaveable { mutableStateOf(options2.first()) }

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
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(end = 100.dp)
            ) {
                ConverterRow(
                    selectedOption = selectedOption1,
                    onOptionSelected = { option ->
                        selectedOption1 = option
                        viewModel.updateDistance(selectedOption1, selectedOption)
                    },
                    value = textFieldValueUp.text,
                    onValueChange = {},
                    options = options1,
                    enabled = false
                )
                if (BuildConfig.IS_PREMIUM) {
                    SwapButton(swapValues)
                }
                ConverterRow(
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                        viewModel.updateDistance(selectedOption1, selectedOption)
                    },
                    value = textFieldValueDown.text,
                    onValueChange = { viewModel.setTextFieldValueDown(it, selectedOption1, selectedOption) },
                    options = options2,
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
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))

                ConverterRow(
                    selectedOption = selectedOption1,
                    onOptionSelected = { option ->
                        selectedOption1 = option
                        viewModel.updateDistance(selectedOption1, selectedOption)
                    },
                    value = textFieldValueUp.text,
                    onValueChange = {},
                    options = options1,
                    enabled = false
                )

                if (BuildConfig.IS_PREMIUM) {
                    SwapButton(swapValues)
                }

                ConverterRow(
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                        viewModel.updateDistance(selectedOption1, selectedOption)
                    },
                    value = textFieldValueDown.text,
                    onValueChange = { viewModel.setTextFieldValueDown(it, selectedOption1, selectedOption) },
                    options = options2,
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

