package com.example.cruisemaster.crewdetails.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.crewdetails.model.User
import com.example.cruisemaster.crewdetails.repositary.AllCrewRespositary
import kotlinx.coroutines.launch


class AllCrewViewModel(private val allCrewRespositary: AllCrewRespositary) : ViewModel() {

    private var voyageNumber: String? = null
    val shipnameMutableLiveData = MutableLiveData<ArrayList<User>>()
    val checkedInCountLiveData = MutableLiveData<Int>()
    val onboardedCountLiveData = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()

    fun setVoyageNumber(voyageNumber: String?) {
        this.voyageNumber = voyageNumber
    }

    fun fetchShipName() {
        viewModelScope.launch {
            try {
                voyageNumber?.let {
                    val response = allCrewRespositary.fetchShipName(it)
                    Log.d("GuestNoViewModel", "Guests: ${response.crewInfo}")
                    shipnameMutableLiveData.postValue(ArrayList(response.crewInfo))
                    checkedInCountLiveData.postValue(response.checkedInCount)
                    onboardedCountLiveData.postValue(response.onboardedCount)
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