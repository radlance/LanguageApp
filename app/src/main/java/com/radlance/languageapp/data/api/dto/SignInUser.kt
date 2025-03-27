package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInUser(
    val email: String,
    val password: String
)
