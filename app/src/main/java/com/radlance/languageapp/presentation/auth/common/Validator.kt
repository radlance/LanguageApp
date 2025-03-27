package com.radlance.languageapp.presentation.auth.common

import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface Validator {

    fun validFirstName(value: String): Boolean

    fun validLastName(value: String): Boolean

    fun validEmail(value: String): Boolean

    fun validPassword(value: String): Boolean

    class Base @Inject constructor(): Validator {

        override fun validFirstName(value: String): Boolean = value.isNotBlank()

        override fun validLastName(value: String): Boolean = value.isNotBlank()

        override fun validEmail(value: String): Boolean {
            return Regex(
                "^[a-z0-9]+@[a-z0-9]+(-[a-z0-9]+)*(\\.[a-z0-9]+(-[a-z0-9]+)*)*\\.[a-z]{2,}$"
            ).matches(value)
        }

        override fun validPassword(value: String): Boolean = value.isNotBlank()
    }
}