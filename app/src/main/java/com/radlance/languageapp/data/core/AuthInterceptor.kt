package com.radlance.languageapp.data.core

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
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

        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }
}