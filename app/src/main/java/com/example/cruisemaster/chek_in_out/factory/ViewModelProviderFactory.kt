package com.example.cruisemaster.chek_in_out.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.chek_in_out.repositary.CheckRepository
import com.example.cruisemaster.chek_in_out.viewmodel.CheckViewModel

class ViewModelProviderFactory(private val repository: CheckRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckViewModel::class.java)) {
            return CheckViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}