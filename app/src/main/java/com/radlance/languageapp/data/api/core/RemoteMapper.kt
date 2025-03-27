package com.radlance.languageapp.data.api.core

import com.radlance.languageapp.data.api.dto.SignInUser
import com.radlance.languageapp.data.api.dto.SignUpUser
import com.radlance.languageapp.domain.auth.User

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
}