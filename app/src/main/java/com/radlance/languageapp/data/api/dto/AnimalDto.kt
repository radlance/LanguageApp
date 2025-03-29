package com.radlance.languageapp.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AnimalDto(
    val id: Int,
    val name: String,
    val image: String?
)
