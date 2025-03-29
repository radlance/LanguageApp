package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameDataDto(
    val questions: List<QuestionDto>
)
