package com.radlance.languageapp.data.theme

import com.radlance.languageapp.data.core.DataStoreManager
import com.radlance.languageapp.domain.theme.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class SystemThemeRepository @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ThemeRepository {

    override suspend fun changeTheme(isDark: Boolean) = dataStoreManager.changeTheme(isDark)

    override fun isDarkUserTheme(): Flow<Boolean?> = dataStoreManager.isDarkUserTheme()

}