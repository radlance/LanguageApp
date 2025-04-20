package com.radlance.languageapp.domain.game

data class Player(
    val id: Int,
    val email: String,
    val score: Int,
    val selectedAnswer: Int?,
    val answerIsRight: Boolean?
)
