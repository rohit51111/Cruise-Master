package com.example.cruisemaster

import com.example.cruisemaster.shiplist.model.Ship
import com.example.cruisemaster.shiplist.network.ShipListApiService
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ShipListApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var shipListApiService: ShipListApiService
    private val gson = Gson()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        shipListApiService = retrofit.create(ShipListApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetchShips returns successful response`() = runBlocking {
        val ships = listOf(
            Ship(
                shipId = "1",
                shipName = "Ship A",
                shipDepartmentConnections = emptyList(),
                voyageInfos = emptyList()
            ),
            Ship(
                shipId = "2",
                shipName = "Ship B",
                shipDepartmentConnections = emptyList(),
                voyageInfos = emptyList()
            )
        )
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(gson.toJson(ships))
        mockWebServer.enqueue(mockResponse)

        val response = shipListApiService.fetchShips()
        assertEquals(ships, response)
    }

    @Test
    fun `fetchShips returns error response`(): Unit = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setBody("Bad Request")
        mockWebServer.enqueue(mockResponse)

        assertFailsWith<HttpException> {
            shipListApiService.fetchShips()
        }
    }

    @Test
    fun `fetchShips handles server error response`(): Unit = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("Server Error")
        mockWebServer.enqueue(mockResponse)

        assertFailsWith<HttpException> {
            shipListApiService.fetchShips()
        }
    }
}