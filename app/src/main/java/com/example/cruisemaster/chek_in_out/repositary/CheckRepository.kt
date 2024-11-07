package com.example.cruisemaster.chek_in_out.repositary

import com.example.cruisemaster.chek_in_out.model.CheckRequest
import com.example.cruisemaster.chek_in_out.network.CheckApiService

class CheckRepository(private val apiService: CheckApiService) {
    suspend fun check(personId: String, checkIn: String) = apiService.check(personId, CheckRequest(checkIn))
}