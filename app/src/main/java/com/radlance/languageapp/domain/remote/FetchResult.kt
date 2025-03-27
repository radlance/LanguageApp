package com.radlance.languageapp.domain.remote

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface FetchResult<D> {
    fun <T : Any> map(mapper: Mapper<D, T>): T

    interface Mapper<D, T : Any> {

        fun mapSuccess(data: D): T

        fun mapError(data: D?): T
    }

    class Success<D: Any>(private val data: D) : FetchResult<D> {
        override fun <T : Any> map(mapper: Mapper<D, T>): T {
            return mapper.mapSuccess(data)
        }
    }

    class Error<D>(private val data: D?) : FetchResult<D> {
        override fun <T : Any> map(mapper: Mapper<D, T>): T {
            return mapper.mapError(data)
        }
    }
}