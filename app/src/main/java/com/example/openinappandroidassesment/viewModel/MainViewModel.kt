package com.example.openinappandroidassesment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinappandroidassesment.models.DashBoardModel
import com.example.openinappandroidassesment.network.NetworkResponse
import com.example.openinappandroidassesment.repository.MainRepository
import com.example.openinappandroidassesment.utils.TOKEN
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _dashboardResult = MutableLiveData<NetworkResponse<DashBoardModel>>()
    val dashboardResult: LiveData<NetworkResponse<DashBoardModel>> = _dashboardResult



    fun getDashBoard() {
        _dashboardResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = repository.getDashBoard(token)
                _dashboardResult.value = when {
                    response.isSuccessful -> {
                        response.body()?.let { NetworkResponse.Success(it) } ?: NetworkResponse.Error("Empty response body")
                    }
                    else -> NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _dashboardResult.value = NetworkResponse.Error("Exception occurred: ${e.message}")
            }
        }
    }

    companion object {
        val token = "Bearer $TOKEN"
    }

}