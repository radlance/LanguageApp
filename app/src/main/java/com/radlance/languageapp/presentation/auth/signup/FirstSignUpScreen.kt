package com.radlance.languageapp.presentation.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
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
    navigateToLastSignUp: (firstName: String, lastName: String, email: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var firstNameFieldValue by rememberSaveable { mutableStateOf("") }
    var lastNameFieldValue by rememberSaveable { mutableStateOf("") }
    var emailFieldValue by rememberSaveable { mutableStateOf("") }

    val authUiState by viewModel.authUiState.collectAsState()

    LaunchedEffect(viewModel.navigateToLastSignUpScreen) {
        if (viewModel.navigateToLastSignUpScreen) {
            navigateToLastSignUp(firstNameFieldValue, lastNameFieldValue, emailFieldValue)
            viewModel.resetNavigationState()
        }
    }

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
                .fillMaxWidth()
                .padding(contentPadding)
                .verticalScroll(scrollState),
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
                    onValueChange = {
                        firstNameFieldValue = it
                        viewModel.resetFirstName()
                    },
                    labelResId = R.string.first_name,
                    hintResId = R.string.your_first_name,
                    showError = !authUiState.isCorrectFirstName
                )

                EnterInputField(
                    value = lastNameFieldValue,
                    onValueChange = {
                        lastNameFieldValue = it
                        viewModel.resetLastNameError()
                    },
                    labelResId = R.string.last_name,
                    hintResId = R.string.your_last_name,
                    showError = !authUiState.isCorrectLastName
                )

                EnterInputField(
                    value = emailFieldValue,
                    onValueChange = {
                        emailFieldValue = it
                        viewModel.resetEmailError()
                    },
                    labelResId = R.string.email_address,
                    hintResId = R.string.email,
                    showError = !authUiState.isCorrectEmail
                )
            }

            Spacer(Modifier.height(34.dp))

            AppButton(
                labelResId = R.string.continue_label,
                onClick = {
                    viewModel.navigateToLastSignUpScreen(
                        firstName = firstNameFieldValue,
                        lastName = lastNameFieldValue,
                        email = emailFieldValue
                    )
                },
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