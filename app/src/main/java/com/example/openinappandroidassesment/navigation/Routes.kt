package com.example.openinappandroidassesment.navigation

sealed class Routes(val route: String){

    // Bottom App Bar Routes

    object Link: Routes("link")
    object Course: Routes("course")
    object Campaign: Routes("campaign")
    object Profile: Routes("profile")

}
