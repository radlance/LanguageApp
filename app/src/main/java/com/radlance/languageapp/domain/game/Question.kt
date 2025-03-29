package com.radlance.languageapp.domain.game

data class Question(
    val wordId: Int,
    val word: String,
    val transcription: String,
    val answers: List<String>,
    val correctAnswerNumber: Int
)
