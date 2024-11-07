package com.example.cruisemaster.shiplist.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.shiplist.model.Ship
import com.example.cruisemaster.shiplist.repository.ShipListRepository
import kotlinx.coroutines.launch

class ShipListViewModel() : ViewModel() {
    val shipNames = MutableLiveData<List<String>>()
    val errorMessage = MutableLiveData<String>()
    private val repository = ShipListRepository()
    val selectedShipName = MutableLiveData<String>()
    private val _ships = MutableLiveData<List<Ship>>()
    val ships: LiveData<List<Ship>> get() = _ships

    fun fetchShips() {
        viewModelScope.launch{
            try {
                val ships = repository.getShips()
                _ships.value = ships
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ShipViewModel", "Error fetching ships", e)
                errorMessage.value = "Failed to fetch ships: ${e.message}"
            }
        }
    }

}
