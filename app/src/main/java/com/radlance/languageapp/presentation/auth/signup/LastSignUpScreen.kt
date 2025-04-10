package com.radlance.languageapp.presentation.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
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
fun LastSignUpScreen(
    firstName: String,
    lastName: String,
    email: String,
    onBackPressed: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val signUpResultUiState by viewModel.signUpResultUiState.collectAsState()
    val authUiState by viewModel.authUiState.collectAsState()

    var passwordFieldValue by rememberSaveable { mutableStateOf("") }
    var confirmPasswordFieldValue by rememberSaveable { mutableStateOf("") }

    var checked by rememberSaveable { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    snackbarData = it,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        },

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

        signUpResultUiState.Show(
            onSuccessResult = navigateToHomeScreen,
            snackBarHostState = snackBarHostState
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.choose_a_password),
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
                    value = passwordFieldValue,
                    onValueChange = {
                        passwordFieldValue = it
                        viewModel.resetPasswordError()
                    },
                    labelResId = R.string.password,
                    hintResId = R.string.password_hint,
                    isPassword = true,
                    showError = !authUiState.isCorrectPassword,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                )

                EnterInputField(
                    value = confirmPasswordFieldValue,
                    onValueChange = {
                        confirmPasswordFieldValue = it
                        viewModel.resetConfirmPasswordError()
                    },
                    labelResId = R.string.confirm_password,
                    hintResId = R.string.password_hint,
                    isPassword = true,
                    showError = !authUiState.isCorrectConfirmPassword,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                )
            }

            Spacer(Modifier.height(25.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = CheckboxDefaults.colors(checkedColor = Blue, uncheckedColor = Blue),
                    modifier = Modifier.offset(x = (-14).dp, y = (-14).dp)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = Blue)) {
                            append(stringResource(R.string.i_have_made_myself))
                        }

                        withStyle(SpanStyle(color = GrayDark)) {
                            append(stringResource(R.string.and_accept))
                        }
                    },
                    fontSize = 17.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W500,
                    lineHeight = 22.sp
                )
            }

            Spacer(Modifier.height(73.dp))
            AppButton(
                labelResId = R.string.signup,
                onClick = {
                    viewModel.signUp(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = passwordFieldValue,
                        confirmPassword = confirmPasswordFieldValue
                    )
                },
                enabled = checked,
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