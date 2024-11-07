package com.example.cruisemaster.viewdetails.repository

import android.util.Log
import com.example.cruisemaster.viewdetails.model.VoyageDetails
import com.example.cruisemaster.viewdetails.network.RetrofitInstance

class VoyageNumberRepository {

    private val voyageService = RetrofitInstance.api
    suspend fun getVoyages(shipId : String): List<VoyageDetails> {
        return try {
            val response = voyageService.fetchVoyages(shipId)
            response
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun fetchDepartments(shipId: String) = voyageService.fetchDepartments(shipId)
}