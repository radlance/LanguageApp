package com.radlance.languageapp.domain.game

interface ConnectionStatus {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapConnected(): T

        fun mapDisconnected(): T

        fun mapError(): T
    }

    object Connected : ConnectionStatus {
        override fun <T : Any> map(mapper: Mapper<T>): T = mapper.mapConnected()
    }

    object Disconnected : ConnectionStatus {
        override fun <T : Any> map(mapper: Mapper<T>): T = mapper.mapDisconnected()
    }

    object Error : ConnectionStatus {
        override fun <T : Any> map(mapper: Mapper<T>): T = mapper.mapError()
    }
}