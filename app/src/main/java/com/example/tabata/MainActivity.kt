package com.example.tabata

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tabata.data.entities.WorkoutSequence
import com.example.tabata.presentation.components.SettingCard
import com.example.tabata.presentation.navigation.graphs.RootNavigationGraph
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import com.example.tabata.presentation.theme.TabataTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()

            val darkTheme by settingsViewModel.isDarkTheme.collectAsState()
            val fontScale by settingsViewModel.fontSize.collectAsState()
            val locale by settingsViewModel.locale.collectAsState()

            TabataTheme(
                darkTheme = darkTheme,
                fontScale = fontScale,
                locale = locale
            ) {
                RootNavigationGraph(navController = rememberNavController())
//                SequenceCreateScreen()
            }

        }
    }
}

//
//@Composable
//fun ColorPickerDialog(
//    isDialogVisible: MutableState<Boolean>,
//    selectedColor: MutableState<String>
//) {
//    if (isDialogVisible.value) {
//        AlertDialog(
//            onDismissRequest = { isDialogVisible.value = false },
//            title = { Text("Choose Color") },
//            text = {
//                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
//                    // Example color options
//                    val colors = listOf("#FF5733", "#33FF57", "#3357FF", "#FF5733", "#F0F0F0")
//                    colors.forEach { color ->
//                        Button(
//                            onClick = {
//                                selectedColor.value = color
//                                isDialogVisible.value = false
//                            },
//                            modifier = Modifier
//                                .padding(4.dp)
//                                .background(Color(android.graphics.Color.parseColor(color)))
//                        ) {
//                            Text(" ", color = Color(android.graphics.Color.parseColor(color)))
//                        }
//                    }
//                }
//            },
//            confirmButton = {
//                Button(
//                    onClick = { isDialogVisible.value = false }
//                ) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun StepperControl(
//    value: Int,
//    onIncrement: () -> Unit,
//    onDecrement: () -> Unit,
//    unit: String = "sec",
//    textWidth: Int = 80
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier.width(250.dp)
//    ) {
//        Button(onClick = onDecrement) {
//            Text("-")
//        }
//
//        Box(
//            modifier = Modifier
//                .width(textWidth.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "$value $unit", style = MaterialTheme.typography.bodyLarge)
//        }
//
//        Button(onClick = onIncrement) {
//            Text("+")
//        }
//    }
//}
//
//
//@Composable
//fun SequenceCreateScreen() {
//    val warmUpDurationState = remember { mutableStateOf(60) } // default в секундах
//    val workoutDurationState = remember { mutableStateOf(20) }
//    val restDurationState = remember { mutableStateOf(10) }
//    val cooldownDurationState = remember { mutableStateOf(60) }
//    val roundsState = remember { mutableStateOf(3) }
//    val restBetweenSetsState = remember { mutableStateOf(30) }
//
//    // Для Popup
//    val isDialogVisible = remember { mutableStateOf(false) }
//    val titleState = remember { mutableStateOf("") }
//    val colorState = remember { mutableStateOf("#FFFFFF") }
//    val isColorPickerVisible = remember { mutableStateOf(false) }
//
//    // Save logic
//    fun createNewSequence() {
//        val newSequence = WorkoutSequence(
//            id = 0,
//            title = titleState.value,
//            color = colorState.value,
//            warmUpDuration = warmUpDurationState.value,
//            workoutDuration = workoutDurationState.value,
//            restDuration = restDurationState.value,
//            cooldownDuration = cooldownDurationState.value,
//            rounds = roundsState.value,
//            restBetweenSets = restBetweenSetsState.value
//        )
//        // Сохраняем newSequence в репозитории или базе данных
//        // Вызываем функцию для сохранения
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//            .padding(5.dp)
//    ) {
//        Spacer(modifier = Modifier.height(15.dp))
//        Text(
//            text = "Create New Sequence",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        PhaseDurationBlock("Warm-up Duration", warmUpDurationState)
//        PhaseDurationBlock("Workout Duration", workoutDurationState)
//        PhaseDurationBlock("Rest Duration", restDurationState)
//        PhaseDurationBlock("Cooldown Duration", cooldownDurationState)
//        PhaseDurationBlock("Rounds", roundsState, unit = "x")
//        PhaseDurationBlock("Rest Between Sets", restBetweenSetsState)
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = { isDialogVisible.value = true },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Create Sequence")
//        }
//    }
//
//    ColorPickerDialog(
//        isDialogVisible = isColorPickerVisible,
//        selectedColor = colorState
//    )
//
//
//    SequencePopupDialog(
//        isDialogVisible = isDialogVisible,
//        titleState = titleState,
//        colorState = colorState,
//        isColorPickerVisible = isColorPickerVisible,  // Pass isColorPickerVisible here
//        onSave = { createNewSequence() }
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewSequenceCreateScreen() {
//    SequenceCreateScreen()
//}
//


