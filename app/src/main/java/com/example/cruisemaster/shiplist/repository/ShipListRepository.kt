package com.example.cruisemaster.shiplist.repository
import android.util.Log
import com.example.cruisemaster.shiplist.model.Ship
import com.example.cruisemaster.shiplist.network.RetrofitInstance

class ShipListRepository {

    private val shipService = RetrofitInstance.api
    suspend fun getShips(): List<Ship> {
        return try {
            val response = shipService.fetchShips()
            response

        } catch (e: Exception) {
            throw e
        }
    }
}