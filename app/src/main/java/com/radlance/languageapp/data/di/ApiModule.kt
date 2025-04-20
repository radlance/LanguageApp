package com.radlance.languageapp.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.core.AuthInterceptor
import com.radlance.languageapp.data.core.DataStoreManager
import com.radlance.languageapp.data.game.BaseStompService
import com.radlance.languageapp.data.game.StompService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import javax.inject.Singleton

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(dataStoreManager: DataStoreManager): AuthInterceptor {
        return AuthInterceptor(dataStoreManager)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(json: Json): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.103:8080/api/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    }

    @Singleton
    @Provides
    fun provideAppService(
        httpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): AppService {
        return retrofit.client(httpClient).build().create<AppService>()
    }

    @Singleton
    @Provides
    fun provideStompClient(): StompClient {
        return Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.0.103:8080/game").withClientHeartbeat(30000)
    }

    @Singleton
    @Provides
    fun provideStompService(
        json: Json,
        stompClient: StompClient
    ): StompService {
        return BaseStompService(json, stompClient)
    }
}