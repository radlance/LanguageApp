package com.radlance.languageapp.domain.main

import com.radlance.languageapp.domain.auth.User

data class MainFetchContent(
    val currentUser: User,
    val leaderboard: List<UserScore>,
    val exercises: List<Exercise>
)
