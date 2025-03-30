package com.radlance.languageapp.domain.main

import com.radlance.languageapp.domain.auth.User

data class UserScore(
    val user: User,
    val score: Double
)
