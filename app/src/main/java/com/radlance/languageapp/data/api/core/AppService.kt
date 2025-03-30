package com.radlance.languageapp.data.api.core

import com.radlance.languageapp.data.api.dto.AnimalDto
import com.radlance.languageapp.data.api.dto.AuthUserResponse
import com.radlance.languageapp.data.api.dto.ExerciseDto
import com.radlance.languageapp.data.api.dto.GameDataDto
import com.radlance.languageapp.data.api.dto.GameDto
import com.radlance.languageapp.data.api.dto.GameIdDto
import com.radlance.languageapp.data.api.dto.ScoreDto
import com.radlance.languageapp.data.api.dto.SignInUser
import com.radlance.languageapp.data.api.dto.SignUpUser
import com.radlance.languageapp.data.api.dto.UserResponse
import com.radlance.languageapp.data.api.dto.UserScoreDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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
    suspend fun refreshToken(
        @Header("Authorization") authHeader: String
    ): AuthUserResponse

    @GET("users/profile")
    suspend fun profile(): UserResponse

    @GET("leaderboard")
    suspend fun leaderboard(): List<UserScoreDto>

    @GET("exercises")
    suspend fun exercises(): List<ExerciseDto>

    @GET("users/avatar/{fileName}")
    suspend fun avatarByFileName(@Path("fileName") fileName: String): ResponseBody

    @Multipart
    @POST("users/profile/avatar")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): ResponseBody

    @POST("game/start")
    suspend fun gameStart(): GameDto

    @POST("connect")
    suspend fun gameConnect(@Body gameIdDto: GameIdDto): GameDto

    @GET("animals")
    suspend fun animals(): List<AnimalDto>

    @GET("animals/image/{fileName}")
    suspend fun animalByFileName(@Path("fileName") fileName: String): ResponseBody

    @GET("game")
    suspend fun game(): GameDataDto

    @POST("leaderboard/increment-score")
    suspend fun incrementScore(@Body score: ScoreDto)
}