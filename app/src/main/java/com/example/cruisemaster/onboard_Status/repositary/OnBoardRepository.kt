package com.example.cruisemaster.onboard_Status.repositary

import com.example.cruisemaster.onboard_Status.model.OnBoardRequest
import com.example.cruisemaster.onboard_Status.network.OnBoardApiService

class OnBoardRepository(private val apiService: OnBoardApiService) {
    suspend fun onBoard(personId: String, isOnBoard: String) = apiService.onBoard(personId, OnBoardRequest(isOnBoard))
}