package com.radlance.languageapp.data.api.core

import com.radlance.languageapp.data.api.dto.ExerciseDto
import com.radlance.languageapp.data.api.dto.SignInUser
import com.radlance.languageapp.data.api.dto.SignUpUser
import com.radlance.languageapp.data.api.dto.UserResponse
import com.radlance.languageapp.data.api.dto.UserScoreDto
import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.domain.main.Exercise
import com.radlance.languageapp.domain.main.UserScore

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

abstract class RemoteMapper {

    protected fun User.toSignInUser(): SignInUser {
        return SignInUser(email = email, password = password)
    }

    protected fun User.toSignUpUser(): SignUpUser {
        return SignUpUser(
            email = email,
            firstName = firstName,
            lastName = lastName,
            password = password
        )
    }

    protected fun UserScoreDto.toUserScore(): UserScore {
        return UserScore(
            user = User(
                firstName = firstName,
                lastName = lastName,
                id = userId,
                avatar = userAvatar ?: ""
            ),
            score = score
        )
    }

    protected fun UserResponse.toUser(): User {
        return User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            id = id,
            avatar = avatar ?: ""
        )
    }

    protected fun ExerciseDto.toExercise(): Exercise {
        return Exercise(id = id, name = name, image = image ?: "")
    }
}