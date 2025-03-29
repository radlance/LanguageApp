package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val wordId: Int,
    val word: String,
    val transcription: String,
    val answers: List<String>,
    val correctAnswerNumber: Int
)
