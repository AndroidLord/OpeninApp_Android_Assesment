package com.example.openinappandroidassesment.navigation

import com.example.openinappandroidassesment.R

sealed class BottomNavItems(
    val route: String,
    val title: String,
    val icon: Int
){
    object Link : BottomNavItems(
        route = Routes.Link.route,
        title = "Links",
        icon = R.drawable.baseline_link_24
    )

    object Courses : BottomNavItems(
        route = Routes.Course.route,
        title = "Courses",
        icon = R.drawable.book
    )

    object Campaigns : BottomNavItems(
        route = Routes.Campaign.route,
        title = "Campaigns",
        icon = R.drawable.campagin
    )

    object Profile : BottomNavItems(
        route = Routes.Profile.route,
        icon = R.drawable.profile,
        title = "Profile"
    )
}
