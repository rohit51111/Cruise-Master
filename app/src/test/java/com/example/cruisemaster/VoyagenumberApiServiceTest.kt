package com.example.cruisemaster

import com.example.cruisemaster.viewdetails.network.VoyagenumberApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VoyagenumberApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: VoyagenumberApiService

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VoyagenumberApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetchVoyages returns successful response`(): Unit = runBlocking {
        // Given
        val jsonResponse = """
            [
                {"voyageNumber": "CR20231111008", "details": "Voyage 1 details"}
            ]
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // When
        val response = apiService.fetchVoyages("CR")

        // Then
        assertNotNull(response)
        assertEquals(1, response.size)
        assertEquals("CR20231111008", response[0].voyageNumber)
    }

    @Test(expected = HttpException::class)
    fun `fetchVoyages returns error response`(): Unit = runBlocking {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // When
        apiService.fetchVoyages("CH")
    }

    @Test
    fun `fetchDepartments returns successful response`() = runBlocking {
        // Given
        val jsonResponse = """
            ["Department A", "Department B"]
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // When
        val response = apiService.fetchDepartments("123")

        // Then
        assertNotNull(response)
        assertEquals(2, response.size)
        assertEquals("Department A", response[0])
    }

    @Test(expected = HttpException::class)
    fun `fetchDepartments returns error response`(): Unit = runBlocking {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // When
        apiService.fetchDepartments("123")
    }
}