package com.radlance.languageapp.domain.theme

import kotlinx.coroutines.flow.Flow

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface ThemeRepository {

    suspend fun changeTheme(isDark: Boolean)

    fun isDarkUserTheme(): Flow<Boolean?>
}