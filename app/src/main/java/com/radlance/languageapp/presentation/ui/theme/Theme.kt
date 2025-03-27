package com.radlance.languageapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

private val DarkColorScheme = darkColorScheme(
    primary = DeepBlue,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = DarkBackground
)

private val LightColorScheme = lightColorScheme(
    primary = DeepBlue,
    background = Color.White,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun LanguageAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val viewModel = hiltViewModel<ThemeViewModel>()

    val userDarkTheme by viewModel.isDarkUserTheme.collectAsState()
    val colorScheme = userDarkTheme?.let {
        if (it) DarkColorScheme else LightColorScheme
    } ?: run {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}