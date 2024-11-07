package com.example.cruisemaster.reservationno.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.cruisemaster.reservationno.repositary.ReservationNoRespositary
import com.example.cruisemaster.reservationno.viewmodel.ReservationNoViewModel


class ReservationNoViewModelfactory(private val reservationNoRespositary: ReservationNoRespositary) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationNoViewModel::class.java)) {
            return ReservationNoViewModel(reservationNoRespositary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}