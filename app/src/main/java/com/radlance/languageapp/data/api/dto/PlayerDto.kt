package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val id: Int,
    val email: String,
    val score: Int,
    val selectedAnswer: Int,
    val answerIsRight: Boolean
)
