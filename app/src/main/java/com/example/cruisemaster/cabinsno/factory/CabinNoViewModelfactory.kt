package com.example.cruisemaster.cabinsno.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.cabinsno.model.CabinNoRespositary
import com.example.cruisemaster.cabinsno.viewmodel.CabinNoViewModel

import com.example.cruisemaster.reservationno.repositary.ReservationNoRespositary
import com.example.cruisemaster.reservationno.viewmodel.ReservationNoViewModel


class CabinNoViewModelfactory(private val cabinNoRespositary: CabinNoRespositary) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CabinNoViewModel::class.java)) {
            return CabinNoViewModel(cabinNoRespositary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}