package com.example.cruisemaster.againcrewdetails.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.againcrewdetails.repositary.CrewDetailsRepositary
import com.example.cruisemaster.againcrewdetails.viewmodel.CrewDetailsViewModel



class CrewDeatilViewModelFactory(private val crewDetailsRepositary: CrewDetailsRepositary):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CrewDetailsViewModel(crewDetailsRepositary) as T
    }



}