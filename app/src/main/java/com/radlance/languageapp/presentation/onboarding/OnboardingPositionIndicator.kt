package com.radlance.languageapp.presentation.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radlance.languageapp.presentation.ui.theme.Dark20
import com.radlance.languageapp.presentation.ui.theme.LanguageAppTheme
import com.radlance.languageapp.presentation.ui.theme.Orange

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun OnboardingPositionIndicator(
    position: Int,
    modifier: Modifier = Modifier
) {

    Row(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(3) { index ->
            val color by animateColorAsState(
                if (position == index) {
                    Orange
                } else {
                    Dark20
                }
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardingPositionIndicatorPreview() {
    LanguageAppTheme {
        OnboardingPositionIndicator(position = 0)
    }
}