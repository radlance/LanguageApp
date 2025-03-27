package com.radlance.languageapp.domain.profile

import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.domain.remote.FetchResult

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface ProfileRepository {

    suspend fun loadUserInfo(): FetchResult<User>
}