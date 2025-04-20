package com.radlance.languageapp.data.game

import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.core.RemoteMapper
import com.radlance.languageapp.domain.game.ConnectionStatus
import com.radlance.languageapp.domain.game.Game
import com.radlance.languageapp.domain.game.GameData
import com.radlance.languageapp.domain.game.GameRepository
import com.radlance.languageapp.domain.remote.FetchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

class RemoteGameRepository @Inject constructor(
    private val appService: AppService,
    private val stompService: StompService
) : GameRepository, RemoteMapper() {

    override suspend fun start(): FetchResult<Game> {
        return try {
            stompService.connect()
            val gameDto = appService.gameStart()
            FetchResult.Success(gameDto.toGame())
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }

    override suspend fun gameConnect(): FetchResult<Game> {
        return try {
            stompService.connect()
            val roomIds = appService.availableRooms()
            val gameDto = appService.gameConnect(gameId = roomIds.first())
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

    override fun collectGame(gameId: String): Flow<Game> {
        val gameFlow = stompService.collectGame(gameId).map {
            it.toGame()
        }

        return gameFlow
    }

    override fun collectConnectionEvents(): Flow<ConnectionStatus> {
        return stompService.collectConnectionEvents().map { it.toConnectionState() }
    }

    override fun disconnect() = stompService.disconnect()

    override fun isConnected(): Boolean = stompService.isConnected()

    override suspend fun fetchGameContent(gameId: String) {
        appService.fetchGameState(gameId)
    }
}