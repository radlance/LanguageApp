package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignUpUser(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)
