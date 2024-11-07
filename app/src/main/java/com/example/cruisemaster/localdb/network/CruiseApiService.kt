package com.example.cruisemaster.localdb.network

import com.example.cruisemaster.localdb.tables.Crew
import com.example.cruisemaster.localdb.tables.Guest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

// https://demo.skosystems.co/cruiser/api/ships/manifest/CR20231111008
interface CruiseApiService {
    @GET("api/ships/manifest/{voyageNumber}")
    fun getManifest(@Path("voyageNumber") voyageNumber: String): Call<ManifestResponse>
}

data class ManifestResponse(
    val guests: List<Guest>,
    val crew: List<Crew>
)
