package com.radlance.languageapp.presentation.component

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.ui.theme.Dark05
import com.radlance.languageapp.presentation.ui.theme.Dark50
import com.radlance.languageapp.presentation.ui.theme.LanguageAppTheme
import com.radlance.languageapp.presentation.ui.theme.Red
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun EnterInputField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    @StringRes hintResId: Int,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    showError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val borderColor by animateColorAsState(
        if (showError) Red else Dark05
    )

    var showPassword by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        val label = stringResource(labelResId)

        if (label.isNotBlank()) {
            Text(
                text = label,
                fontSize = 15.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(8.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Dark05)
                .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
        ) {
            Spacer(Modifier.width(20.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(hintResId),
                        fontSize = 15.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W400,
                        lineHeight = 20.sp,
                        color = Dark50
                    )
                }

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W400,
                        lineHeight = 20.sp
                    ),
                    singleLine = true,
                    visualTransformation = if (!showPassword && isPassword) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    keyboardOptions = keyboardOptions,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (isPassword) {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Image(
                        painter = painterResource(R.drawable.ic_eye),
                        contentDescription = "ic_eye"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EnterInputFieldPreview() {
    LanguageAppTheme {
        EnterInputField(
            value = "",
            isPassword = true,
            onValueChange = {},
            labelResId = R.string.email_address,
            hintResId = R.string.email
        )
    }
}