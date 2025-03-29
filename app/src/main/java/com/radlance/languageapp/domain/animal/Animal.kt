package com.radlance.languageapp.domain.animal

import android.graphics.Bitmap

data class Animal(
    val id: Int,
    val name: String,
    val image: Bitmap?
)
