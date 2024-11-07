package com.example.cruisemaster.login.repository

import com.example.cruisemaster.login.network.AuthService
import com.example.cruisemaster.login.network.LoginRequest
import com.example.cruisemaster.login.network.RetrofitInstance


class AuthRepository {
    private val authService: AuthService = RetrofitInstance.api

    suspend fun login(email: String, password: String) = authService.login(LoginRequest(email, password))
}