package com.radlance.languageapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.dto.SignInUser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create

class AppServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var appService: AppService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val json = Json { ignoreUnknownKeys = true }

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        appService = retrofit.create<AppService>()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `login - should return AuthUserResponse`(): Unit = runTest {
        val mockResponse = """
            {
                "id" : 1,
                "email": "test@email.com",
                "firstName": "name1",
                "lastName": "lastName1",
                "avatar": "avatar1",
                "token": "token"
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(mockResponse)
        )

        val response = appService.login(SignInUser("email@example.com", "password"))

        assertEquals(1, response.id)
        assertEquals("test@email.com", response.email)
        assertEquals("name1", response.firstName)
        assertEquals("lastName1", response.lastName)
        assertEquals("avatar1", response.avatar)
        assertEquals("token", response.token)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/auth/login", recordedRequest.path)
        assertEquals("POST", recordedRequest.method)
    }

    @Test
    fun `refreshToken - should send Authorization header`() = runTest {
        val mockResponse = """
            {
                "id" : 1,
                "email": "test@email.com",
                "firstName": "name1",
                "lastName": "lastName1",
                "avatar": "avatar1",
                "token": "new-token"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponse))

        val response = appService.refreshToken("Bearer old-token")

        assertEquals("new-token", response.token)

        val request = mockWebServer.takeRequest()
        assertEquals("Bearer old-token", request.getHeader("Authorization"))
    }

    @Test
    fun `fetchGameState - sends the correct path`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        val gameId = "12345678"
        appService.fetchGameState(gameId)

        val request = mockWebServer.takeRequest()
        assertEquals("/game/$gameId", request.path)
        assertEquals("GET", request.method)

        /** example for query parameters
         *
         * <pre>
         * val url = request.requestUrl!!
         * assertEquals("shoes", url.queryParameter("q"))
         * assertEquals("20", url.queryParameter("limit"))
         * </pre>
         */
    }
}