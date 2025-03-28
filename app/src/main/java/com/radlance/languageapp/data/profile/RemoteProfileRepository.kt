package com.radlance.languageapp.data.profile

import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.core.RemoteMapper
import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.domain.profile.ProfileRepository
import com.radlance.languageapp.domain.remote.FetchResult
import java.io.File
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class RemoteProfileRepository @Inject constructor(
    private val appService: AppService
) : ProfileRepository, RemoteMapper() {

    override suspend fun loadUserInfo(): FetchResult<User> {
        return try {
            val userDto = appService.profile()
            FetchResult.Success(
                userDto.toUser(
                    userDto.avatar?.let { appService.avatarByFileName(it) }
                )
            )
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }

    override suspend fun updateUserImage(file: File): FetchResult<File> {
        return try {
            appService.uploadImage(file.toMultipartBodyPart())
            FetchResult.Success(file)
        } catch (e: Exception) {
            FetchResult.Error(file)
        }
    }
}