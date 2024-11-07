package com.example.cruisemaster.againguestdetails.repositary

import com.example.cruisemaster.againguestdetails.model.GuestDetails
import com.example.cruisemaster.againguestdetails.network.GuestDetailsApiService



class GuestDetailsRepositary(private val guestDetailsApiService: GuestDetailsApiService) {


    suspend fun fetchguestdetails(personId: String): GuestDetails {
        return guestDetailsApiService.fetchGuestDetails(personId)
    }


}


