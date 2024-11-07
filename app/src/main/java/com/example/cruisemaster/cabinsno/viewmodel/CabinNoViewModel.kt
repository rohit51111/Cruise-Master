package com.example.cruisemaster.cabinsno.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cruisemaster.cabinsno.model.CabinNoRespositary
import com.example.cruisemaster.cabinsno.model.User2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CabinNoViewModel(private val cabinNoRespositary: CabinNoRespositary) : ViewModel() {
    val guestsLiveData = MutableLiveData<List<User2>>()
    val countLiveData = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()
    val cabinNumberLiveData = MutableLiveData<String>()

    fun fetchGuestsByCabinNumber(cabinNumber: String) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = cabinNoRespositary.fetchGuestsByCabinNumber(cabinNumber)
            withContext(Dispatchers.Main) {
                guestsLiveData.value = response.guests
                countLiveData.value = response.count
                cabinNumberLiveData.value = response.cabinNo.toString()
                isLoading.value = false

            }
        }
    }
}