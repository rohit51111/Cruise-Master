package com.example.cruisemaster.crewdetails.network
import com.example.cruisemaster.crewdetails.model.UserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface AllCrewApiService {

    @GET("GetCrews/{yoyageNumber}")
    suspend fun fetchGuest(@Path("yoyageNumber") voyageNumber: String): UserResponse


    companion object {
        private var guestnoApiService: AllCrewApiService? = null

        fun getInstance(): AllCrewApiService {
            if (guestnoApiService == null) {
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

                guestnoApiService = retrofit.create(AllCrewApiService::class.java)
            }
            return guestnoApiService!!
        }
    }
}
