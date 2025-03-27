package com.radlance.languageapp.domain.auth

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val avatar: String = "",
    val id: Int = 0
)
