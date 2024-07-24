package com.example.openinappandroidassesment.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.openinappandroidassesment.screens.CampaignScreen
import com.example.openinappandroidassesment.screens.CourseScreen
import com.example.openinappandroidassesment.screens.LinkScreen
import com.example.openinappandroidassesment.screens.ProfileScreen
import com.example.openinappandroidassesment.viewModel.MainViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: MainViewModel) {

    NavHost(
        navController = navController,
        startDestination = Routes.Link.route
    ){

        composable(
            route = Routes.Link.route
        ){
            LinkScreen(navController,viewModel)
        }

        composable(
            route = Routes.Course.route
        ){
            CourseScreen()
        }

        composable(
            route = Routes.Campaign.route
        ){
            CampaignScreen()
        }

        composable(
            route = Routes.Profile.route
        ){
            ProfileScreen()
        }

    }


}