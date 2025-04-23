package com.radlance.languageapp.domain.game

import com.radlance.languageapp.domain.remote.FetchResult
import kotlinx.coroutines.flow.Flow

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

interface GameRepository {

    suspend fun start(): FetchResult<Game>

    suspend fun gameConnect(): FetchResult<Game>

    suspend fun gamePractice(): FetchResult<GameData>

    fun collectGame(gameId: String): Flow<Game>

    fun collectConnectionEvents(): Flow<ConnectionStatus>

    fun disconnectClient()

    suspend fun fetchGameContent(gameId: String): FetchResult<Unit>

    suspend fun cancelGame(gameId: String): FetchResult<Unit>

    suspend fun answer(gameId: String, selectedAnswerIndex: Int): FetchResult<Unit>

    suspend fun nextQuestion(gameId: String): FetchResult<Unit>
}