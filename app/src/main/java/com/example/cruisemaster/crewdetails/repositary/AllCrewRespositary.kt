package com.example.cruisemaster.crewdetails.repositary


import android.util.Log
import com.example.cruisemaster.crewdetails.model.UserResponse
import com.example.cruisemaster.crewdetails.network.AllCrewApiService


class AllCrewRespositary(private val allCrewApiService: AllCrewApiService) {

    suspend fun fetchShipName(voyageNumber: String): UserResponse {
        val response = allCrewApiService.fetchGuest(voyageNumber)
        return response
    }
}