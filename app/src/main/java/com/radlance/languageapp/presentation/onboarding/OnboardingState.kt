package com.radlance.languageapp.presentation.onboarding

import com.radlance.languageapp.R

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

interface OnboardingState {

    fun imageResId(): Int

    fun titleResId(): Int

    fun descriptionResId(): Int

    fun buttonResId(): Int

    fun navigate(next: () -> Unit, chooseLanguage: () -> Unit)

    fun skip(action: () -> Unit)

    fun position(): Int

    object First : OnboardingState {

        override fun imageResId(): Int = R.drawable.onboarding_1

        override fun titleResId(): Int = R.string.confidence_your_words

        override fun descriptionResId(): Int = R.string.with_conservation

        override fun buttonResId(): Int = R.string.next

        override fun navigate(next: () -> Unit, chooseLanguage: () -> Unit) = next()

        override fun skip(action: () -> Unit) = action()

        override fun position(): Int = 0
    }

    object Second : OnboardingState {

        override fun imageResId(): Int = R.drawable.onboarding_2

        override fun titleResId(): Int = R.string.take_your_time

        override fun descriptionResId(): Int = R.string.develop_a_habit

        override fun buttonResId(): Int = R.string.more

        override fun navigate(next: () -> Unit, chooseLanguage: () -> Unit) = next()

        override fun skip(action: () -> Unit) = action()

        override fun position(): Int = 1
    }

    object Last : OnboardingState {

        override fun imageResId(): Int = R.drawable.onboarding_3

        override fun titleResId(): Int = R.string.the_lessons_you_need

        override fun descriptionResId(): Int = R.string.using_a_variety

        override fun buttonResId(): Int = R.string.choose_a_language

        override fun navigate(next: () -> Unit, chooseLanguage: () -> Unit) = chooseLanguage()

        override fun skip(action: () -> Unit) = action()

        override fun position(): Int = 2
    }
}