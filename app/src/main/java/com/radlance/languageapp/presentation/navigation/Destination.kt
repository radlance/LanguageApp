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

@Keep
@Serializable
object SignIn : Destination

@Keep
@Serializable
object FirstSignUp : Destination

@Keep
@Serializable
data class LastSignUp(
    val firstName: String,
    val lastName: String,
    val email: String
) : Destination

@Keep
@Serializable
object Main : Destination

@Keep
@Serializable
object Profile : Destination

@Keep
@Serializable
object ResizePicture : Destination

@Keep
@Serializable
object GameMultiplayer : Destination

@Keep
@Serializable
object HostRoom : Destination

@Keep
@Serializable
data class Game(val isCreator: Boolean) : Destination

@Keep
@Serializable
object GuessTheAnimalQuestion : Destination

@Keep
@Serializable
object SuccessGuessTheAnimal : Destination

@Keep
@Serializable
data class ErrorGuessTheAnimal(val rightAnswer: String) : Destination

@Keep
@Serializable
object Practice : Destination