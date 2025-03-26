package com.radlance.languageapp.presentation.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.ui.theme.Blue
import com.radlance.languageapp.presentation.ui.theme.Dark60
import com.radlance.languageapp.presentation.ui.theme.LanguageAppTheme
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 25.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun OnboardingScreen(
    onboardingState: OnboardingState,
    onNextClick: () -> Unit,
    onChooseLanguageClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(targetState = onboardingState) { currentState ->
        Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.weight(1.8f)) {
                Image(
                    painter = painterResource(currentState.imageResId()),
                    contentDescription = "onboarding_image",
                    modifier = Modifier.align(Alignment.Center)
                )

                OnboardingPositionIndicator(
                    position = currentState.position(),
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            Spacer(Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(currentState.titleResId()),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W500,
                    lineHeight = 28.sp
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = stringResource(currentState.descriptionResId()),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W400,
                    color = Dark60,
                    lineHeight = 20.sp
                )

                Spacer(Modifier.height(50.dp))

                Button(
                    onClick = {
                        currentState.navigate(
                            next = onNextClick,
                            chooseLanguage = onChooseLanguageClick
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors().copy(containerColor = Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(currentState.buttonResId()),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W500,
                        lineHeight = 24.sp
                    )
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.skip_onboarding),
                    fontSize = 15.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W400,
                    lineHeight = 20.sp,
                    modifier = Modifier.clickable { onSkipClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    LanguageAppTheme {
        OnboardingScreen(onboardingState = OnboardingState.First, {}, {}, {})
    }
}