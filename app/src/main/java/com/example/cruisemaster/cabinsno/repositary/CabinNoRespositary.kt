package com.example.cruisemaster.cabinsno.model
import com.example.cruisemaster.reservationno.model.ReservationNoApiService
import com.example.cruisemaster.reservationno.model.User3


class CabinNoRespositary(private val cabinNoApiService: CabinNoApiService) {
    suspend fun fetchGuestsByCabinNumber(cabinNumber: String): UserResponse2 {
        return cabinNoApiService.getGuestsByCabinNumber(cabinNumber)
    }
}