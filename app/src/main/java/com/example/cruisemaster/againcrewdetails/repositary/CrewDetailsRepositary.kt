package com.example.cruisemaster.againcrewdetails.repositary

import com.example.cruisemaster.againcrewdetails.model.CrewDetails
import com.example.cruisemaster.againcrewdetails.model.CrewDetailsApiService




class CrewDetailsRepositary(private val crewDetailsApiService: CrewDetailsApiService) {



    suspend fun fetchguestdetails(personId: String): CrewDetails {
        return crewDetailsApiService.fetchGuestDetails(personId)
    }


}


