package com.example.cruisemaster.viewdetails.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.viewdetails.model.VoyageDetails
import com.example.cruisemaster.viewdetails.repository.VoyageNumberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoyageNumberViewModel() : ViewModel() {
    val voyageNumbers = MutableLiveData<List<String>>()
    val errorMessage = MutableLiveData<String>()
    var repository = VoyageNumberRepository()
    val selectedVoyageNumber = MutableLiveData<String>()
    private val _voyageDetails = MutableLiveData<List<VoyageDetails>>()


    val departments = MutableLiveData<List<String>>()
    val voyages: LiveData<List<VoyageDetails>> get() = _voyageDetails

    fun fetchVoyages(shipId : String) {

        viewModelScope.launch{
            try {
                val voyages = repository.getVoyages(shipId)
                _voyageDetails.value = voyages
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = "Failed to fetch VoyageNumber: ${e.message}"
            }
        }
    }

    fun fetchDepartments(shipId: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.fetchDepartments(shipId)
                }
                departments.postValue(response)
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }

}