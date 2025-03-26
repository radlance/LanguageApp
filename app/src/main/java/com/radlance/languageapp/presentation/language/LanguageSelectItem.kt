package com.radlance.languageapp.presentation.language

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.domain.language.Language
import com.radlance.languageapp.presentation.ui.theme.LanguageAppTheme
import com.radlance.languageapp.presentation.ui.theme.Orange
import com.radlance.languageapp.presentation.ui.theme.UnselectedLanguage
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun LanguageSelectItem(
    languageItem: Language,
    selected: Boolean,
    enabled: Boolean,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        if (selected) {
            Orange
        } else {
            UnselectedLanguage
        }
    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .height(67.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .then(if (enabled) Modifier.clickable { onClick(languageItem.id) } else Modifier)
    ) {
        Row {
            Spacer(Modifier.width(15.dp))
            Text(
                text = languageItem.name,
                fontSize = 22.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 28.sp
            )
        }
    }
}

@Preview
@Composable
private fun LanguageSelectItemSelectedPreview() {
    LanguageAppTheme {
        LanguageSelectItem(
            selected = true,
            onClick = {},
            languageItem = Language(name = "Russian", code = "ru"),
            enabled = true
        )
    }
}

@Preview
@Composable
private fun LanguageSelectItemUnselectedPreview() {
    LanguageAppTheme {
        LanguageSelectItem(
            selected = false,
            onClick = {},
            languageItem = Language(name = "Russian", code = "ru"),
            enabled = true
        )
    }
}