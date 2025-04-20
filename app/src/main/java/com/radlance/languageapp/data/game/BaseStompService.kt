package com.radlance.languageapp.data.game

import com.radlance.languageapp.data.api.dto.GameDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject

class BaseStompService @Inject constructor(
    private val json: Json,
    private val stompClient: StompClient
) : StompService {
    override fun collectGame(gameId: String): Flow<GameDto> = callbackFlow {
        val disposable = stompClient.topic("/topic/game-progress/$gameId")
            .subscribeOn(Schedulers.io(), false)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { message ->
                    try {
                        val chatMessage = json.decodeFromString<GameDto>(message.payload)
                        trySend(chatMessage)
                    } catch (e: Exception) {
                        close(e)
                    }
                },
                { error -> close(error) }
            )

        awaitClose { disposable.dispose() }
    }

    override fun collectConnectionEvents(): Flow<LifecycleEvent> = callbackFlow {
        val disposable = stompClient.lifecycle()
            .subscribeOn(Schedulers.io(), false)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                trySend(event)
            }

        awaitClose { disposable.dispose() }
    }

    override fun connect() {
        if (!stompClient.isConnected) {
            stompClient.connect()
        }
    }

    override fun disconnect() {
        if (stompClient.isConnected) {
            stompClient.disconnect()
        }
    }

    override fun isConnected(): Boolean = stompClient.isConnected
}