package com.example.cruisemaster.againguestdetails.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.againguestdetails.repositary.GuestDetailsRepositary
import com.example.cruisemaster.againguestdetails.viewmodel.GuestDetailsViewModel


class GuestDeatilViewModelFactory(private val guestDetailsRepositary: GuestDetailsRepositary):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GuestDetailsViewModel(guestDetailsRepositary) as T
    }



}