package com.radlance.languageapp.data.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.dto.AuthUserResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreManager.getJwt().first()
        }

        val request = chain.request().newBuilder().apply {
            token?.let { addHeader("Authorization", "Bearer $token") }
        }

        val response = chain.proceed(request.build())

        if (response.code == 410) {
            runBlocking {
                try {
                    val newToken = getNewToken("$token")
                    dataStoreManager.saveJwt(newToken.token)
                } catch (e: Exception) {
                    dataStoreManager.deleteToken()
                }
            }

            val newToken = runBlocking { dataStoreManager.getJwt().first() }
            val newRequest = request.removeHeader("Authorization")
                .header("Authorization", "Bearer $newToken")
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }

    private suspend fun getNewToken(token: String): AuthUserResponse {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.103:8080/api/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

        val service = retrofit.create<AppService>()
        return service.refreshToken(authHeader = "Bearer $token")
    }
}