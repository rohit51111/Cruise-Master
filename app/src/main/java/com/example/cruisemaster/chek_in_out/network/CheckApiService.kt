package com.example.cruisemaster.chek_in_out.network

import com.example.cruisemaster.chek_in_out.model.CheckRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface CheckApiService {
    @PATCH("ships/check/{personId}")
    suspend fun check(@Path("personId") personId: String, @Body request: CheckRequest): Response<Unit>

    companion object {
        private var retrofitService: CheckApiService? = null

        fun getInstance(): CheckApiService {
            if (retrofitService == null) {
                val interceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://demo.skosystems.co/cruiser/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                retrofitService = retrofit.create(CheckApiService::class.java)
            }
            return retrofitService!!
        }
    }
}

