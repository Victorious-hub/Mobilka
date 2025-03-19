package com.example.converter

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.outlined.CurrencyBitcoin
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.SocialDistance
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.converter.presentation.components.ConverterRow
import com.example.converter.presentation.components.Numpad
import com.example.converter.presentation.screens.currency.CurrencyConverterScreen
import com.example.converter.presentation.navigation.NavBarBody
import com.example.converter.presentation.navigation.NavBarHeader
import com.example.converter.presentation.navigation.NavGraph
import com.example.converter.presentation.navigation.NavigationItem
import com.example.converter.presentation.navigation.Screens
import com.example.converter.presentation.screens.currency.CurrencyViewModel
import com.example.converter.presentation.ui.theme.ConverterTheme
import com.example.converter.presentation.ui.theme.NavigationDrawerJetpackComposeTheme
import com.example.converter.utils.Categories
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if (BuildConfig.IS_PREMIUM) {
                Toast.makeText(this, "Premium features enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Free version", Toast.LENGTH_SHORT).show()
            }
            App()
        }
    }
}

//@Preview
@Composable
fun Numpad() {
    val buttonModifier = Modifier
        .size(80.dp)
        .padding(5.dp)
        .background(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(12.dp) // Уменьшаем радиус скругления
        )
        .shadow(4.dp, shape = RoundedCornerShape(12.dp), clip = true) // Тень для объема
        .border(2.dp, MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(12.dp)) // Обводка для контраста

    val buttonStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        listOf(
            listOf("7", "8", "9"),
            listOf("4", "5", "6"),
            listOf("1", "2", "3"),
            listOf("0", ".", "AC")
        ).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { text ->
                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text(
                            text = text,
                            style = buttonStyle,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}



@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
//@Composable
//fun Test()
//{
//    val textFieldValueUp = ""
//    val textFieldValueDown = ""
//    val options = listOf("USD", "BYN", "RUB", "EUR")
//    val options1 = listOf("USD", "BYN", "RUB", "EUR")
//    var selectedOption by remember { mutableStateOf("USD") }
//    var selectedOption1 by remember { mutableStateOf("USD") }
//
//    val configuration = LocalConfiguration.current
//    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
//
//    if (isLandscape) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(25.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Сдвигаем все влево (оставляем место для numpad справа)
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(0.7f)  // Оставляем 70% ширины для содержимого
//                    .padding(end = 125.dp)
//
//            ) {
//                // Для горизонтальной ориентации используем Row для выбора единиц
//                ConverterRow(
//                    selectedOption = selectedOption1,
//                    onOptionSelected = { option ->
//                        selectedOption1 = option
////                        viewModel.updateCurrency(selectedOption1, selectedOption)
//                    },
//                    value = textFieldValueUp,
//                    onValueChange = {},
//                    options = options1,
//                    enabled = false,
//                    isHighlighted =
//                )
//
//                Spacer(modifier = Modifier.height(20.dp))
//
//                ConverterRow(
//                    selectedOption = selectedOption,
//                    onOptionSelected = { option ->
//                        selectedOption = option
////                        viewModel.updateCurrency(selectedOption1, selectedOption)
//                    },
//                    value = textFieldValueUp,
//                    onValueChange = {  },
//                    options = options,
//                    enabled = false
//                )
//            }
//
//            // Сдвигаем numpad максимально вправо
//            Column(
//                modifier = Modifier.fillMaxHeight(),
//                verticalArrangement = Arrangement.Bottom,
//                horizontalAlignment = Alignment.End
//            ) {
//                Numpad(
//                    onNumberClick = { number ->
////                        viewModel.setTextFieldValueDown(number, selectedOption1, selectedOption)
//                    },
//                    onClearClick = {
////                        viewModel.setTextFieldValueDown("AC", selectedOption1, selectedOption)
//                    },
//                    onBackspaceClick = {
//                        val currentText = textFieldValueUp
//                        if (currentText.isNotEmpty()) {
//                            val newText = currentText.dropLast(1)
////                            viewModel.setTextFieldValueDown(newText.ifEmpty { "0" }, selectedOption1, selectedOption)
//                        }
//                    },
//                    isLandscape = isLandscape // Передаем ориентацию
//                )
//            }
//        }
//    } else {
//        // Вертикальное расположение (для портретной ориентации)
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Spacer(modifier = Modifier.height(50.dp))
//
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Spacer(modifier = Modifier.height(25.dp))
//
//                // Вертикальное расположение для ConverterRow
//                ConverterRow(
//                    selectedOption = selectedOption1,
//                    onOptionSelected = { option ->
//                        selectedOption1 = option
////                        viewModel.updateCurrency(selectedOption1, selectedOption)
//                    },
//                    value = textFieldValueUp,
//                    onValueChange = {},
//                    options = options1,
//                    enabled = false
//                )
//
//                Spacer(modifier = Modifier.height(50.dp))
//
//                ConverterRow(
//                    selectedOption = selectedOption,
//                    onOptionSelected = { option ->
//                        selectedOption = option
////                        viewModel.updateCurrency(selectedOption1, selectedOption)
//                    },
//                    value = textFieldValueUp,
//                    onValueChange = { },
//                    options = options,
//                    enabled = false
//                )
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            Numpad(
//                onNumberClick = { number ->
////                    viewModel.setTextFieldValueDown(number, selectedOption1, selectedOption)
//                },
//                onClearClick = {
////                    viewModel.setTextFieldValueDown("AC", selectedOption1, selectedOption)
//                },
//                onBackspaceClick = {
//                    val currentText = textFieldValueUp
//                    if (currentText.isNotEmpty()) {
//                        val newText = currentText.dropLast(1)
////                        viewModel.setTextFieldValueDown(newText.ifEmpty { "0" }, selectedOption1, selectedOption)
//                    }
//                },
//                isLandscape = isLandscape
//            )
//        }
//    }
//}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun App() {
    MaterialTheme {}
    NavigationDrawerJetpackComposeTheme {
        val items = listOf(
            NavigationItem(
                title = "Currency",
                route = Screens.Currency.route,
                selectedIcon = Icons.Filled.CurrencyBitcoin,
                unSelectedIcon = Icons.Outlined.CurrencyBitcoin,
                category = Categories.CURRENCY
            ),
            NavigationItem(
                title = "Distance",
                route = Screens.Distance.route,
                selectedIcon = Icons.Filled.SocialDistance,
                unSelectedIcon = Icons.Outlined.SocialDistance,
                category = Categories.MASS
            ),
            NavigationItem(
                title = "Weight",
                route = Screens.Weight.route,
                selectedIcon = Icons.Filled.LineWeight,
                unSelectedIcon = Icons.Outlined.LineWeight,
                category = Categories.MASS
            ),
        )
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val topBarTitle =
            if (currentRoute != null){
                items[items.indexOfFirst {
                    it.route == currentRoute
                }].title
            }else{
                items[0].title
            }
        ModalNavigationDrawer(
            gesturesEnabled = true,
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    NavBarHeader()
                    Spacer(modifier = Modifier.height(8.dp))
                    NavBarBody(
                        items = items,
                        currentRoute = currentRoute
                    ) { currentNavigationItem ->
                        if (currentNavigationItem.route == "share") {
//                            showToast(
//                                message = "This is Short Toast",
//                                backgroundColor = Color.White,
//                                textColor = Color.Black,
//                                duration = ToastDuration.Short
//                            )
                        } else {
                            navController.navigate(currentNavigationItem.route) {
                                navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                                    popUpTo(startDestinationRoute) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            },) {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(text = topBarTitle)
                    }, navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menu"
                            )
                        }
                    })
                },

                ) {
                    innerPadding->
                NavGraph(navController = navController, innerPadding = innerPadding)
            }
        }
    }
}