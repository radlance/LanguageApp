package com.radlance.languageapp.data.language

import com.radlance.languageapp.domain.language.Language
import com.radlance.languageapp.domain.language.LanguageRepository
import javax.inject.Inject

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

class LanguageRepositoryImpl @Inject constructor() : LanguageRepository {

    override fun loadLanguages(): List<Language> {
        val languages = listOf(
            Language(id = 1, name = "Russian", code = "ru"),
            Language(id = 2, name = "English", code = "en"),
            Language(id = 3, name = "Chinese", code = "zh"),
            Language(id = 4, name = "Belarusian", code = "be"),
            Language(id = 5, name = "Kazakh", code = "kk")
        )

        return languages
    }
}