package com.example.openinappandroidassesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.openinappandroidassesment.repository.MainRepository
import com.example.openinappandroidassesment.screens.BottomAppBarScreen
import com.example.openinappandroidassesment.ui.theme.OpeninAppAndroidAssesmentTheme
import com.example.openinappandroidassesment.utils.ErrorScreen
import com.example.openinappandroidassesment.utils.isInternetAvailable
import com.example.openinappandroidassesment.viewModel.MainViewModel
import com.example.openinappandroidassesment.viewModel.MainViewModelFactory
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = MainRepository()
        val viewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)

        viewModel.getDashBoard()

        setContent {
            OpeninAppAndroidAssesmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var isInternetAvailable by remember { mutableStateOf(isInternetAvailable(this)) }
                    var hasFetchedData by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        while (true) {
                            val currentInternetStatus = isInternetAvailable(this@MainActivity)
                            if (currentInternetStatus && !hasFetchedData) {
                                viewModel.getDashBoard()
                                hasFetchedData = true
                            }
                            isInternetAvailable = currentInternetStatus
                            delay(5000) // Check every 5 seconds
                        }
                    }

                    when (isInternetAvailable) {
                        true -> MainScreen(innerPadding, viewModel)
                        false -> ErrorScreen("No Internet Connection")
                    }



                }
            }
        }
    }
}





@Composable
fun MainScreen(innerPadding: PaddingValues, viewModel: MainViewModel) {

    BottomAppBarScreen(
        navController = rememberNavController(),
        viewModel = viewModel
    )
}


