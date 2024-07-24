package com.example.openinappandroidassesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.openinappandroidassesment.repository.MainRepository
import com.example.openinappandroidassesment.screens.BottomAppBarScreen
import com.example.openinappandroidassesment.ui.theme.OpeninAppAndroidAssesmentTheme
import com.example.openinappandroidassesment.viewModel.MainViewModel
import com.example.openinappandroidassesment.viewModel.MainViewModelFactory

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
                    MainScreen(innerPadding,viewModel)
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


