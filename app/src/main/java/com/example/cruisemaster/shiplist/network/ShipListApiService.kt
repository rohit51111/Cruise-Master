package com.example.cruisemaster.shiplist.network
import com.example.cruisemaster.shiplist.model.Ship
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ShipListApiService {
    @GET("GetAllShips")
    suspend fun fetchShips(): List<Ship>
}
        object RetrofitInstance {
            private const val BASE_URL = "https://demo.skosystems.co/cruiser/api/ships/"

            val api: ShipListApiService by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ShipListApiService::class.java)
            }
        }
