package com.example.cruisemaster.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cruisemaster.login.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel() : ViewModel() {
    private val repository: AuthRepository = AuthRepository()
    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val body = response.body()?.string()
                    Log.d("LoginViewModel", "Success Body: $body")
                    _loginResult.postValue(Result.success(body ?: ""))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("LoginViewModel", "Error: ${response.code()} - ${response.message()}")
                    Log.e("LoginViewModel", "Error Body: $errorBody")
                    _loginResult.postValue(Result.failure(HttpException(response)))
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                _loginResult.postValue(Result.failure(e))
            }
        }
    }
}