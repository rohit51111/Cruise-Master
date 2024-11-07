package com.example.cruisemaster.login.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseBody>
}

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)