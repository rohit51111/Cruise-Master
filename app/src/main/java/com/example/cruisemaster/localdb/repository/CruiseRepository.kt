package com.example.cruisemaster.localdb.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.cruisemaster.localdb.database.AppDatabase
import com.example.cruisemaster.localdb.network.CruiseApiService
import com.example.cruisemaster.localdb.network.ManifestResponse
import com.example.cruisemaster.localdb.tables.Crew
import com.example.cruisemaster.localdb.tables.Guest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CruiseRepository(private val context: Context) {
    private val guestInfoDao = AppDatabase.getDatabase(context).guestInfoDao()
    private val crewInfoDao = AppDatabase.getDatabase(context).crewInfoDao()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://demo.skosystems.co/cruiser/")
        .client(okHttpClient)  // Set the OkHttp client with logging
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(CruiseApiService::class.java)

    fun downloadManifest(voyageNumber: String, onResult: (Boolean, String?) -> Unit) {
        apiService.getManifest(voyageNumber).enqueue(object : Callback<ManifestResponse> {
            override fun onResponse(call: Call<ManifestResponse>, response: Response<ManifestResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { manifest ->
                        if (manifest.guests.isEmpty() && manifest.crew.isEmpty()) {
                            // Show toast message on the main thread
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "Manifest does not have data", Toast.LENGTH_SHORT).show()
                            }
                            onResult(false, "Manifest is empty")
                        } else {
                            insertGuestsAndCrew(manifest.guests, manifest.crew)
                            onResult(true, null)
                        }
                    } ?: run {
                        onResult(false, "Empty response body")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DownloadManifest", "Response code: ${response.code()}, Response error: $errorBody")
                    onResult(false, errorBody ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<ManifestResponse>, t: Throwable) {
                Log.e("DownloadManifest", "Network error: ${t.message}")
                onResult(false, t.message)
            }
        })
    }

    private fun insertGuestsAndCrew(guests: List<Guest>, crew: List<Crew>) {
        CoroutineScope(Dispatchers.IO).launch {
            // Check if the tables are empty
            val guestCount = guestInfoDao.getCount()
            val crewCount = crewInfoDao.getCount()
            if (guestCount > 0 || crewCount > 0) {
                // Delete existing data
                guestInfoDao.deleteAllGuests()
                crewInfoDao.deleteAllCrew()
            }
            // Insert new data
            guests.forEach { guestInfoDao.insertGuestInfo(it) }
            crew.forEach { crewInfoDao.insertCrewInfo(it) }
        }
    }
}