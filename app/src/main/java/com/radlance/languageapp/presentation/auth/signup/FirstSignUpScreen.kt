package com.radlance.languageapp.presentation.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.component.EnterInputField
import com.radlance.languageapp.presentation.ui.theme.Blue
import com.radlance.languageapp.presentation.ui.theme.GrayDark
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstSignUpScreen(
    navigateToSignIn: () -> Unit,
    onBackPressed: () -> Unit,
    navigateToLastSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var firstNameFieldValue by rememberSaveable { mutableStateOf("") }
    var lastNameFieldValue by rememberSaveable { mutableStateOf("") }
    var emailFieldValue by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.signup),
                        color = Color.White,
                        fontSize = 17.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W500,
                        lineHeight = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "back",
                            tint = Color.White,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }
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
            Spacer(Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.create_an_account),
                fontSize = 22.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 28.sp
            )

            Spacer(Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                EnterInputField(
                    value = firstNameFieldValue,
                    onValueChange = { firstNameFieldValue = it },
                    labelResId = R.string.first_name,
                    hintResId = R.string.your_first_name
                )

                EnterInputField(
                    value = lastNameFieldValue,
                    onValueChange = { lastNameFieldValue = it },
                    labelResId = R.string.last_name,
                    hintResId = R.string.your_last_name
                )

                EnterInputField(
                    value = emailFieldValue,
                    onValueChange = { emailFieldValue = it },
                    labelResId = R.string.email_address,
                    hintResId = R.string.email
                )
            }

            Spacer(Modifier.height(34.dp))

            AppButton(
                labelResId = R.string.continue_label,
                onClick = navigateToLastSignUp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(24.dp))

            Row {
                Text(
                    text = stringResource(R.string.already_you_member),
                    fontSize = 17.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W400,
                    color = GrayDark,
                    lineHeight = 22.sp
                )

                Text(
                    text = stringResource(R.string.login),
                    fontSize = 17.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W400,
                    color = Blue,
                    lineHeight = 22.sp,
                    modifier = Modifier.clickable { navigateToSignIn() }
                )
            }
        }
    }
}