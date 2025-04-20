package com.radlance.languageapp.data.game

import com.radlance.languageapp.data.api.dto.GameDto
import kotlinx.coroutines.flow.Flow
import ua.naiksoftware.stomp.dto.LifecycleEvent

interface StompService {

    fun collectGame(gameId: String): Flow<GameDto>

    fun collectConnectionEvents(): Flow<LifecycleEvent>

    fun connect()

    fun disconnect()

    fun isConnected(): Boolean
}