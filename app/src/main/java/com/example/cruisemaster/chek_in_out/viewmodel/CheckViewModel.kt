package com.example.cruisemaster.chek_in_out.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.chek_in_out.repositary.CheckRepository
import kotlinx.coroutines.launch


class CheckViewModel(private val repository: CheckRepository) : ViewModel() {
    private val _checkStatus = MutableLiveData<Boolean>()
    val checkStatus: LiveData<Boolean> get() = _checkStatus

    fun check(personId: String, checkIn: String) {
        viewModelScope.launch {
            val response = repository.check(personId, checkIn)
            _checkStatus.value = response.isSuccessful
        }
    }
}

