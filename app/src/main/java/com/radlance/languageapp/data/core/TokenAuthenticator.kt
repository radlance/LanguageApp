package com.radlance.languageapp.data.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.dto.AuthUserResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class TokenAuthenticator @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val newAuthUserResponse = runBlocking {
            try {
                getNewToken()
            } catch (e: Exception) {
                dataStoreManager.deleteToken()
                null
            }
        } ?: return null


        runBlocking { dataStoreManager.saveJwt(newAuthUserResponse.token) }

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newAuthUserResponse.token}")
            .build()
    }

    private suspend fun getNewToken(): AuthUserResponse {
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
        return service.refreshToken()
    }
}