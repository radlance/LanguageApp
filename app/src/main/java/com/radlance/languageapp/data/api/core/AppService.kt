package com.radlance.languageapp.data.api.core

import com.radlance.languageapp.data.api.dto.AuthUserResponse
import com.radlance.languageapp.data.api.dto.SignInUser
import com.radlance.languageapp.data.api.dto.SignUpUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface AppService {

    @POST("auth/register")
    suspend fun register(@Body signUpUser: SignUpUser): AuthUserResponse

    @POST("auth/login")
    suspend fun login(@Body signInUser: SignInUser): AuthUserResponse

    @GET("auth/refresh-token")
    suspend fun refreshToken(): AuthUserResponse

    @GET("users/profile")
    suspend fun profile(): AuthUserResponse
}