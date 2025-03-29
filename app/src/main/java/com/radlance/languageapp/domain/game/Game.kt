package com.radlance.languageapp.domain.game

data class Game(
    val id: String,
    val player: Player,
    val status: String,
    val gameData: GameData,
    val winnerPlayer: Int,
    val currentQuestion: Int,
    val questionIsFinished: Boolean
)
