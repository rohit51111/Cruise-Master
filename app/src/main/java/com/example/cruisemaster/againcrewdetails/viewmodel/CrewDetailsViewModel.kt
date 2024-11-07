package com.example.cruisemaster.againcrewdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.againcrewdetails.model.CrewDetails
import com.example.cruisemaster.againcrewdetails.repositary.CrewDetailsRepositary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CrewDetailsViewModel(private val crewDetailsRepositary: CrewDetailsRepositary):ViewModel() {



    val guestDetailsMutableLiveData = MutableLiveData<CrewDetails>()
    val isLoading = MutableLiveData<Boolean>()

    private val _isCheckedIn = MutableLiveData<Boolean>()
    val isCheckedIn: LiveData<Boolean> get() = _isCheckedIn

    private val _isOnBoard = MutableLiveData<Boolean>()
    val isOnBoard: LiveData<Boolean> get() = _isOnBoard


    fun fetchGuestDetails(personId: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val guestDetails = crewDetailsRepositary.fetchguestdetails(personId)
                withContext(Dispatchers.Main) {
                    guestDetailsMutableLiveData.value = guestDetails
                    _isCheckedIn.value = guestDetails.isCheckedIn.equals("yes", ignoreCase = true)
                    _isOnBoard.value = guestDetails.isOnboard.equals("yes", ignoreCase = true)
                    isLoading.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                isLoading.value = false
            }
        }
    }

    fun updateCheckInStatus(status: Boolean) {
        _isCheckedIn.value = status
    }

    fun updateOnBoardStatus(status: Boolean) {
        _isOnBoard.value = status
    }



}