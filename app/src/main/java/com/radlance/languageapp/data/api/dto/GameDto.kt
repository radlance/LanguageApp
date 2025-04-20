package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: String,
    @SerialName("player1") val firstPlayer: PlayerDto?,
    @SerialName("player2") val secondPlayer: PlayerDto?,
    val status: String,
    val gameData:GameDataDto,
    val winnerPlayer: Int?,
    val currentQuestion: Int,
    val questionIsFinished: Boolean?
)
