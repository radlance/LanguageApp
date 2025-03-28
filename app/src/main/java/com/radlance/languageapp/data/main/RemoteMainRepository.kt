package com.radlance.languageapp.data.main

import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.core.RemoteMapper
import com.radlance.languageapp.domain.main.MainFetchContent
import com.radlance.languageapp.domain.main.MainRepository
import com.radlance.languageapp.domain.remote.FetchResult
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class RemoteMainRepository @Inject constructor(
    private val appService: AppService
) : MainRepository, RemoteMapper() {

    override suspend fun fetchContent(): FetchResult<MainFetchContent> {
        return try {
            val userDto = appService.profile()
            val leaderboardDto = appService.leaderboard()
            val exercisesDto = appService.exercises()

            val fetchContent = MainFetchContent(
                currentUser = userDto.toUser(userDto.avatar?.let { appService.avatarByFileName(it) }),
                leaderboard = leaderboardDto.map { dto ->
                    dto.toUserScore(dto.userAvatar?.let { appService.avatarByFileName(it) })
                },
                exercises = exercisesDto.map { it.toExercise() }
            )

            FetchResult.Success(fetchContent)

        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }
}