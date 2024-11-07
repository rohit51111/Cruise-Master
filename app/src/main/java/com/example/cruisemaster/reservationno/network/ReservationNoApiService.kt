package com.example.cruisemaster.reservationno.model
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReservationNoApiService {
    @GET("{reservationNumber}")
    suspend fun getGuestsByReservationNumber(@Path("reservationNumber") reservationNumber: String): UserResponse3

    companion object {
        private var reservationApiService: ReservationNoApiService? = null

        fun getInstance(): ReservationNoApiService {
            if (reservationApiService == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://demo.skosystems.co/cruiser/api/guests/GetGuestsByReservationNumber/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                reservationApiService = retrofit.create(ReservationNoApiService::class.java)
            }
            return reservationApiService!!
        }
    }
}
