package com.example.cruisemaster

import com.example.cruisemaster.login.network.AuthService
import com.example.cruisemaster.login.network.LoginRequest
import com.example.cruisemaster.login.network.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertFailsWith


class AuthServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var authService: AuthService
    private val gson = Gson()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `login returns successful response`() = runBlocking {
        val loginRequest = LoginRequest("test@example.com", "password")
        val loginResponse = LoginResponse("mockToken")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(gson.toJson(loginResponse))
        mockWebServer.enqueue(mockResponse)

        val response: Response<ResponseBody> = authService.login(loginRequest)

        assertEquals(200, response.code())
        val responseBody = response.body()?.string()
        assertEquals(gson.toJson(loginResponse), responseBody)
    }

    @Test
    fun `login returns error response`() = runBlocking {
        val loginRequest = LoginRequest("test@example.com", "password")
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setBody("Bad Request")
        mockWebServer.enqueue(mockResponse)

        val response: Response<ResponseBody> = authService.login(loginRequest)

        assertEquals(400, response.code())
        assertEquals("Bad Request", response.errorBody()?.string())
    }

    @Test
    fun `login handles network error response`() = runBlocking {
        val loginRequest = LoginRequest("test@example.com", "password")
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("Server Error")
        mockWebServer.enqueue(mockResponse)

        val response: Response<ResponseBody> = authService.login(loginRequest)

        assertEquals(500, response.code())
        assertEquals("Server Error", response.errorBody()?.string())
    }
}