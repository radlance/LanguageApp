package com.radlance.languageapp.common

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface ResourceProvider {

    fun getString(@StringRes id: Int): String

    class Base @Inject constructor(private val context: Context): ResourceProvider {

        override fun getString(id: Int): String = context.getString(id)
    }
}