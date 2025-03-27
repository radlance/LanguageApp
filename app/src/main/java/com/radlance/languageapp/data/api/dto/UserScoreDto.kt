package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserScoreDto(
    val userId: Int,
    val userAvatar: String?,
    val firstName: String,
    val lastName: String,
    val score: Int
)
