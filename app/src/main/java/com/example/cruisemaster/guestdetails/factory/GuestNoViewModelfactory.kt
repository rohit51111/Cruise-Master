package com.example.cruisemaster.guestdetails.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.cruisemaster.guestdetails.repositary.GuestNoRespositary
import com.example.cruisemaster.guestdetails.viewmodel.GuestNoViewModel


class GuestNoViewModelfactory(private val guestNoRepository: GuestNoRespositary) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuestNoViewModel::class.java)) {
            return GuestNoViewModel(guestNoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}