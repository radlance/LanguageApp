package com.radlance.languageapp.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.presentation.ui.theme.Blue
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun AppButton(
    @StringRes labelResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors().copy(containerColor = Blue),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = stringResource(labelResId),
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = fredokaFamily,
            fontWeight = FontWeight.W500,
            lineHeight = 24.sp,
        )
    }
}