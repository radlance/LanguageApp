package com.radlance.languageapp.domain.profile

import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.domain.remote.FetchResult
import java.io.File

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface ProfileRepository {

    suspend fun loadUserInfo(): FetchResult<User>

    suspend fun updateUserImage(file: File): FetchResult<File>

    suspend fun updateUserScore(score: Double): FetchResult<Unit>
}