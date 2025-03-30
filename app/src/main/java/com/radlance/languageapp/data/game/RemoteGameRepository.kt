package com.radlance.languageapp.data.game

import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.core.RemoteMapper
import com.radlance.languageapp.data.api.dto.GameIdDto
import com.radlance.languageapp.domain.game.Game
import com.radlance.languageapp.domain.game.GameData
import com.radlance.languageapp.domain.game.GameRepository
import com.radlance.languageapp.domain.remote.FetchResult
import javax.inject.Inject

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

class RemoteGameRepository @Inject constructor(
    private val appService: AppService
) : GameRepository, RemoteMapper() {

    override suspend fun start(): FetchResult<Game> {
        return try {
            val gameDto = appService.gameStart()
            FetchResult.Success(gameDto.toGame())
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }

    override suspend fun connect(gameId: String): FetchResult<Game> {
        return try {
            val gameDto = appService.gameConnect(GameIdDto(gameId))
            FetchResult.Success(gameDto.toGame())
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }

    override suspend fun game(): FetchResult<GameData> {
        return try {
            val gameDataDto = appService.game()
            FetchResult.Success(gameDataDto.toGameData())
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }
}