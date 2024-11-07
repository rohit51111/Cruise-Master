package com.example.cruisemaster.onboard_Status.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.onboard_Status.repositary.OnBoardRepository
import kotlinx.coroutines.launch

class OnBoardViewModel(private val repository: OnBoardRepository) : ViewModel() {
    private val _onBoardStatus = MutableLiveData<Boolean>()
    val onBoardStatus: LiveData<Boolean> get() = _onBoardStatus

    fun onBoard(personId: String, isOnBoard: String) {
        viewModelScope.launch {
            val response = repository.onBoard(personId, isOnBoard)
            _onBoardStatus.value = response.isSuccessful
        }
    }
}