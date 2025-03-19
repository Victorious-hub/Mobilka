package com.example.converter.presentation.navigation

sealed class Screens(var route: String) {
    data object  Currency : Screens("currency")
    data object  Weight : Screens("weight")
    data object  Distance : Screens("distance")
}