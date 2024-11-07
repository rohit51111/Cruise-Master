package com.example.cruisemaster.onboard_Status.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.onboard_Status.repositary.OnBoardRepository
import com.example.cruisemaster.onboard_Status.viewmodel.OnBoardViewModel

class OnBoardViewModelProviderFactory(private val repository: OnBoardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnBoardViewModel::class.java)) {
            return OnBoardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}