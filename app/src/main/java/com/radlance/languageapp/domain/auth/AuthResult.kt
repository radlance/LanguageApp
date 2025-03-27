package com.radlance.languageapp.domain.auth

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface AuthResult {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(): T

        fun mapError(message: String): T
    }

    object Success : AuthResult {

        override fun <T : Any> map(mapper: Mapper<T>): T = mapper.mapSuccess()
    }

    class Error(private val message: String) : AuthResult {

        override fun <T : Any> map(mapper: Mapper<T>): T = mapper.mapError(message)
    }
}