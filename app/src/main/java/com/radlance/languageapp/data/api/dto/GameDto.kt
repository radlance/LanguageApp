package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: String,
    val player: PlayerDto,
    val status: String,
    val gameData:GameDataDto,
    val winnerPlayer: Int,
    val currentQuestion: Int,
    val questionIsFinished: Boolean
)
