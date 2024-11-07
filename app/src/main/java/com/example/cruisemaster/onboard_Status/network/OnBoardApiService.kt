package com.example.cruisemaster.onboard_Status.network

import com.example.cruisemaster.onboard_Status.model.OnBoardRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface OnBoardApiService {
    @PATCH("ships/updateOnboardStatus/{personId}")
    suspend fun onBoard(@Path("personId") personId: String, @Body request: OnBoardRequest): Response<Unit>

    companion object {
        private var retrofitService: OnBoardApiService? = null

        fun getInstance(): OnBoardApiService {
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
                retrofitService = retrofit.create(OnBoardApiService::class.java)
            }
            return retrofitService!!
        }
    }
}