package com.example.cruisemaster.crewdetails.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.crewdetails.repositary.AllCrewRespositary
import com.example.cruisemaster.crewdetails.viewmodel.AllCrewViewModel


class MyViewModelfactory(private val allCrewRespositary: AllCrewRespositary) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllCrewViewModel::class.java)) {
            return AllCrewViewModel(allCrewRespositary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}