package com.example.cruisemaster.guestdetails.repositary

import android.util.Log
import com.example.cruisemaster.guestdetails.model.UserResponse4
import com.example.cruisemaster.guestdetails.network.GuestNoApiService


class GuestNoRespositary(private val guestNoApiService: GuestNoApiService) {

    suspend fun fetchShipName(voyageNumber: String): UserResponse4 {
        val response = guestNoApiService.fetchGuest(voyageNumber)
        Log.d("GuestNoRepository", "Response: $response")
        return response
    }
}