package com.radlance.languageapp.domain.auth

import android.graphics.Bitmap

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val avatarBitmap: Bitmap? = null,
    val id: Int = 0
)
