package com.example.cruisemaster.againcrewdetails.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CrewDetailsApiService {




    @GET("GetAllDetailsOfCrew/{personId}")
    suspend fun fetchGuestDetails(@Path("personId") personId: String): CrewDetails


    companion object {
        private var guestDetailsApiService: CrewDetailsApiService? = null


        fun getInstance(): CrewDetailsApiService {
            if (guestDetailsApiService == null) {

                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://demo.skosystems.co/cruiser/api/crews/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                guestDetailsApiService = retrofit.create(CrewDetailsApiService::class.java)
            }
            return guestDetailsApiService!!

        }
    }

}