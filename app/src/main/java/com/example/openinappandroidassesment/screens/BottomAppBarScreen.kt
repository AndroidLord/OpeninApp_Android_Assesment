package com.example.openinappandroidassesment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.openinappandroidassesment.navigation.BottomNavItems
import com.example.openinappandroidassesment.navigation.NavGraph
import com.example.openinappandroidassesment.ui.theme.BaseColor
import com.example.openinappandroidassesment.ui.theme.BottomWhite
import com.example.openinappandroidassesment.ui.theme.FabColor
import com.example.openinappandroidassesment.ui.theme.UnselectedColor
import com.example.openinappandroidassesment.viewModel.MainViewModel

@Composable
fun BottomAppBarScreen(navController: NavHostController, viewModel: MainViewModel) {

    Scaffold(
        floatingActionButton = {
            floatingActionButton_Widget()
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            MyBottomNav(navController)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BaseColor)
                .padding(innerPadding)
        ) {
            NavGraph(navController, viewModel)
        }
    }
}

@Composable
private fun floatingActionButton_Widget() {
    Box(

    ) {
        FloatingActionButton(
            onClick = { /* stub */ },
            shape = CircleShape,
            containerColor = FabColor,
            modifier = Modifier
                .align(Alignment.Center)
                .size(56.dp)
                .offset(y = 60.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
    }
}


@Composable
private fun MyBottomNav(navController: NavController) {

    val selectedItem = remember { mutableStateOf(0) }

    val bottomBarScreens = listOf(
        BottomNavItems.Link,
        BottomNavItems.Courses,
        BottomNavItems.Campaigns,
        BottomNavItems.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = bottomBarScreens.any {
        it.route == currentDestination?.route
    }

    if (!bottomBarDestination) return

    //BottomBar(bottomBarScreens, selectedItem, navController)

     NavigationBar(bottomBarScreens, selectedItem, navController)

}

@Composable
private fun BottomBar(
    bottomBarScreens: List<BottomNavItems>,
    selectedItem: MutableState<Int>,
    navController: NavController
) {
    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Black,

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(77.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomBarScreens.forEachIndexed() { index, item ->

                IconButton(
                    onClick = {

                        selectedItem.value = index

                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = if (selectedItem.value == bottomBarScreens.indexOf(item)) Color.Black else Color.Gray,
                            modifier = Modifier
                                .size(25.dp)
                                .graphicsLayer {
                                    if (item == BottomNavItems.Link) {
                                        rotationZ = -30f
                                    }
                                }
                        )
                        Text(
                            text = item.title,
                            color = if (selectedItem.value == bottomBarScreens.indexOf(item)) Color.Black else Color.Gray,
                            fontSize = 12.sp,
                            modifier = Modifier
                        )
                    }

                }


            }
        }
    }
}




@Composable
private fun NavigationBar(
    bottomBarScreens: List<BottomNavItems>,
    selectedItem: MutableState<Int>,
    navController: NavController
) {
    NavigationBar(
        containerColor = BottomWhite
    ) {


        bottomBarScreens.forEachIndexed { index, item ->


            NavigationBarItem(
                modifier = Modifier.padding(bottom = 4.dp),
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = if (selectedItem.value == index) Color.Black else UnselectedColor,
                        modifier = Modifier
                            .size(30.dp)
                            .graphicsLayer { if (item == BottomNavItems.Link) rotationZ = -30f }

                    )
                },
                label = { Text(
                    item.title,
                    fontSize = 12.sp,
                    color = if (selectedItem.value == index) Color.Black else UnselectedColor
                ) },
                selected = selectedItem.value == index,
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                onClick = {
                    selectedItem.value = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
