package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthUserResponse(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String?,
    val token: String
)
