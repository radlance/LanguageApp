package com.radlance.languageapp.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.component.EnterInputField
import com.radlance.languageapp.presentation.ui.theme.Blue
import com.radlance.languageapp.presentation.ui.theme.GrayDark
import com.radlance.languageapp.presentation.ui.theme.Red
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    var emailFieldValue by rememberSaveable { mutableStateOf("") }
    var passwordFieldValue by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.login),
                        color = Color.White,
                        fontSize = 17.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W500,
                        lineHeight = 22.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                        contentDescription = "back",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 56.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.login),
                    contentDescription = "login",
                    modifier = Modifier
                        .width(105.dp)
                        .height(82.dp)
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "For free, join now and start learning",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W500,
                    lineHeight = 28.sp
                )
            }

            Spacer(Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                EnterInputField(
                    value = emailFieldValue,
                    onValueChange = { emailFieldValue = it },
                    labelResId = R.string.email_address,
                    hintResId = R.string.email,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                EnterInputField(
                    value = passwordFieldValue,
                    onValueChange = { passwordFieldValue = it },
                    labelResId = R.string.password,
                    hintResId = R.string.password_hint,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    isPassword = true
                )
            }

            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.forgot_password),
                fontSize = 15.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 20.sp,
                color = Red,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp)
            )

            Spacer(Modifier.height(32.dp))

            AppButton(
                labelResId = R.string.login,
                onClick = {},
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(24.dp))
            Row {
                Text(
                    text = stringResource(R.string.not_you_member),
                    fontSize = 17.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W500,
                    color = GrayDark,
                    lineHeight = 22.sp
                )

                Text(
                    text = stringResource(R.string.signup),
                    fontSize = 17.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W500,
                    color = Blue,
                    lineHeight = 22.sp
                )
            }
        }
    }
}