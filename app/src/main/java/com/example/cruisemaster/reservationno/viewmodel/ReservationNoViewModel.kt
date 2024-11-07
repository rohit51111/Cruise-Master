package com.example.cruisemaster.reservationno.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.cruisemaster.reservationno.model.User3
import com.example.cruisemaster.reservationno.repositary.ReservationNoRespositary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ReservationNoViewModel(private val reservationNoRespositary: ReservationNoRespositary) : ViewModel() {
    val guestsLiveData = MutableLiveData<List<User3>>()
    val countLiveData = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()
    val reservationNumberLiveData = MutableLiveData<String>()

    fun fetchGuestsByReservationNumber(reservationNumber: String) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = reservationNoRespositary.fetchGuestsByReservationNumber(reservationNumber)
            withContext(Dispatchers.Main) {
                guestsLiveData.value = response.guests
                countLiveData.value = response.count
                reservationNumberLiveData.value = response.reservationNumber
                isLoading.value = false
            }
        }
    }
}