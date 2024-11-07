package com.example.cruisemaster

import com.example.cruisemaster.reservationno.model.ReservationNoApiService
import com.example.cruisemaster.reservationno.model.User3
import com.example.cruisemaster.reservationno.model.UserResponse3
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

class ReservationNoApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var reservationNoApiService: ReservationNoApiService
    private val gson = Gson()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        reservationNoApiService = retrofit.create(ReservationNoApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getGuestsByReservationNumber returns successful response`() = runBlocking {
        val reservationNumber = "123456"

        // Sample data for UserResponse3 and User3
        val sampleGuests = arrayListOf(
            User3(
                cabinNo = 101,
                firstName = "John",
                isCheckedIn = "true",
                isOnboard = "false",
                lastName = "Doe",
                personId = "P001"
            ),
            User3(
                cabinNo = 102,
                firstName = "Jane",
                isCheckedIn = "false",
                isOnboard = "true",
                lastName = "Smith",
                personId = "P002"
            )
        )

        val sampleResponse = UserResponse3(
            cabinNo = 101,
            count = 2,
            guests = sampleGuests,
            reservationNumber = reservationNumber
        )

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(gson.toJson(sampleResponse))
        mockWebServer.enqueue(mockResponse)

        val response = reservationNoApiService.getGuestsByReservationNumber(reservationNumber)
        assertEquals(sampleResponse, response)
    }

    @Test
    fun `getGuestsByReservationNumber returns error response`(): Unit = runBlocking {
        val reservationNumber = "123456"
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setBody("Bad Request")
        mockWebServer.enqueue(mockResponse)

        assertFailsWith<HttpException> {
            reservationNoApiService.getGuestsByReservationNumber(reservationNumber)
        }
    }

    @Test
    fun `getGuestsByReservationNumber handles server error response`(): Unit = runBlocking {
        val reservationNumber = "123456"
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("Server Error")
        mockWebServer.enqueue(mockResponse)

        assertFailsWith<HttpException> {
            reservationNoApiService.getGuestsByReservationNumber(reservationNumber)
        }
    }
}