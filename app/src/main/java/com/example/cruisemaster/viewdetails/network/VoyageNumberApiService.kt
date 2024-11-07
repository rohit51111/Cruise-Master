package com.example.cruisemaster.viewdetails.network


import com.example.cruisemaster.viewdetails.model.VoyageDetails
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface VoyagenumberApiService {
    @GET("GetAllVoyages/{shipId}")
    suspend fun fetchVoyages(@Path("shipId") shipId: String): List<VoyageDetails>

    @GET("GetDepartments/{shipId}")
    suspend fun fetchDepartments(@Path("shipId") shipId: String): List<String>
}
object RetrofitInstance {
    private const val BASE_URL = "https://demo.skosystems.co/cruiser/api/ships/"

    val api: VoyagenumberApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VoyagenumberApiService::class.java)
    }
}
