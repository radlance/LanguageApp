package com.radlance.languageapp.presentation.navigation

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Дата создания: 25.03.2025
 * Автор: Манякин Дмитрий
 */

interface Destination

@Keep
@Serializable
object Splash : Destination

@Keep
@Serializable
object Onboarding : Destination

@Keep
@Serializable
object ChooseLanguage : Destination