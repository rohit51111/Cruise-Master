package com.example.cruisemaster.againguestdetails.network

import com.example.cruisemaster.againguestdetails.model.GuestDetails
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GuestDetailsApiService {

    @GET("GetAllDetailsOfGuest/{personId}")
    suspend fun fetchGuestDetails(@Path("personId") personId: String): GuestDetails

    companion object {
        private var guestDetailsApiService: GuestDetailsApiService? = null


        fun getInstance(): GuestDetailsApiService {
            if (guestDetailsApiService == null) {

                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://demo.skosystems.co/cruiser/api/guests/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                guestDetailsApiService = retrofit.create(GuestDetailsApiService::class.java)
            }
            return guestDetailsApiService!!

        }
    }

}