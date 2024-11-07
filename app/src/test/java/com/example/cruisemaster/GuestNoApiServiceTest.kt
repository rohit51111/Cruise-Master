package com.example.cruisemaster

import com.example.cruisemaster.guestdetails.model.User4
import com.example.cruisemaster.guestdetails.model.UserResponse4
import com.example.cruisemaster.guestdetails.network.GuestNoApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.Test

class GuestNoApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var guestNoApiService: GuestNoApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        guestNoApiService = retrofit.create(GuestNoApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetchGuest returns correct data on successful response`(): Unit = runBlocking {
        // Arrange
        val voyageNumber = "12345"
        val userResponse = UserResponse4(
            cabinNo = 101,
            count = 1,
            checkedInCount = 1,
            onboardedCount = 1,
            guests = arrayListOf(
                User4(
                cabinNo = 101,
                firstName = "John",
                isCheckedIn = "true",
                isOnboard = "true",
                lastName = "Doe",
                personId = "personId1"
            )
            ),
            reservationNumber = "reservation123"
        )

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{
                "cabinNo": 101,
                "count": 1,
                "checkedInCount": 1,
                "onboardedCount": 1,
                "guests": [
                    {
                        "cabinNo": 101,
                        "firstName": "John",
                        "isCheckedIn": "true",
                        "isOnboard": "true",
                        "lastName": "Doe",
                        "personId": "personId1"
                    }
                ],
                "reservationNumber": "reservation123"
            }""")
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = guestNoApiService.fetchGuest(voyageNumber)

        // Assert
        assertEquals(userResponse, response)
    }

    @Test
    fun `fetchGuest handles error response`() = runBlocking {
        // Arrange
        val voyageNumber = "12345"

        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("Not Found")
        mockWebServer.enqueue(mockResponse)

        // Act
        val exception = kotlin.runCatching {
            guestNoApiService.fetchGuest(voyageNumber)
        }.exceptionOrNull()

        // Assert
        assert(exception is retrofit2.HttpException)
        assertEquals(404, (exception as retrofit2.HttpException).code())
    }
}