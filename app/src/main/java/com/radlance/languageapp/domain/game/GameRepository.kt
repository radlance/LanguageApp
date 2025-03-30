package com.radlance.languageapp.domain.game

import com.radlance.languageapp.domain.remote.FetchResult

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

interface GameRepository {

    suspend fun start(): FetchResult<Game>

    suspend fun connect(gameId: String): FetchResult<Game>

    suspend fun game(): FetchResult<GameData>
}