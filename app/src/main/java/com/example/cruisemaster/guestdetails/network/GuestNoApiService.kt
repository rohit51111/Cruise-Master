package com.example.cruisemaster.guestdetails.network

import com.example.cruisemaster.guestdetails.model.UserResponse4
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface GuestNoApiService {


    @GET("GetGuests/{yoyageNumber}")
    suspend fun fetchGuest(@Path ("yoyageNumber") voyageNumber: String): UserResponse4


    companion object {
        private var guestnoApiService: GuestNoApiService? = null

        fun getInstance(): GuestNoApiService {
            if (guestnoApiService == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://demo.skosystems.co/cruiser/api/ships/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                guestnoApiService = retrofit.create(GuestNoApiService::class.java)
            }
            return guestnoApiService!!
        }
    }
}
