package com.example.affirmations

sealed class Screens(val route: String) {
    object NewsScreen : Screens("home_screen")
    object NewsDetailsScreen : Screens("details_screen")
}