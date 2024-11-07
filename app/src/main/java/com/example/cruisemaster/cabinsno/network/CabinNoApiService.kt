package com.example.cruisemaster.cabinsno.model
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CabinNoApiService {
    @GET("{cabinNumber}")
    suspend fun getGuestsByCabinNumber(@Path("cabinNumber") cabinNumber: String): UserResponse2

    companion object {
        private var cabinNoApiService: CabinNoApiService? = null

        fun getInstance(): CabinNoApiService {
            if (cabinNoApiService == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://demo.skosystems.co/cruiser/api/guests/GetGuestsByCabinNumber/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                cabinNoApiService = retrofit.create(CabinNoApiService::class.java)
            }
            return cabinNoApiService!!
        }
    }
}
