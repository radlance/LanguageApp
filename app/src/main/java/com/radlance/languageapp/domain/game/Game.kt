package com.radlance.languageapp.domain.game

data class Game(
    val id: String,
    val firstPlayer: Player?,
    val secondPlayer: Player?,
    val status: String,
    val gameData: GameData,
    val winnerPlayer: Int?,
    val currentQuestion: Int,
    val questionIsFinished: Boolean?
)
