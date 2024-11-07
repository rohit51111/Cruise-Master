package com.example.cruisemaster.reservationno.repositary
import com.example.cruisemaster.reservationno.model.ReservationNoApiService
import com.example.cruisemaster.reservationno.model.User3
import com.example.cruisemaster.reservationno.model.UserResponse3


class ReservationNoRespositary(private val reservationNoApiService: ReservationNoApiService) {
    suspend fun fetchGuestsByReservationNumber(reservationNumber: String): UserResponse3 {
        return reservationNoApiService.getGuestsByReservationNumber(reservationNumber)
    }
}