package com.example.cruisemaster.guestdetails.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.guestdetails.model.User4
import com.example.cruisemaster.guestdetails.repositary.GuestNoRespositary
import kotlinx.coroutines.launch

class GuestNoViewModel(private val guestNoRepository: GuestNoRespositary) : ViewModel() {

    private var voyageNumber: String? = null
    val shipnameMutableLiveData = MutableLiveData<ArrayList<User4>>()
    val checkedInCount = MutableLiveData<Int>()
    val onboardedCount = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()

    fun setVoyageNumber(voyageNumber: String?) {
        this.voyageNumber = voyageNumber
    }

    fun fetchShipName() {
        viewModelScope.launch {
            try {
                voyageNumber?.let {
                    val response = guestNoRepository.fetchShipName(it)
                    Log.d("GuestNoViewModel", "Guests: ${response.guests}")
                    shipnameMutableLiveData.postValue(ArrayList(response.guests))
                    checkedInCount.postValue(response.checkedInCount)
                    onboardedCount.postValue(response.onboardedCount)
                } ?: run {
                    errorMessage.postValue("Voyage number is null")
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                Log.e("GuestNoViewModel", "Error fetching data", e)
            }
        }
    }
}