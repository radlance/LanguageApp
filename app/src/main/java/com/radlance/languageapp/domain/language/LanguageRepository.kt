package com.radlance.languageapp.domain.language

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

interface LanguageRepository {

    fun loadLanguages(): List<Language>
}