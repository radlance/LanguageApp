package com.radlance.languageapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.radlance.languageapp.presentation.language.ChooseLanguageScreen
import com.radlance.languageapp.presentation.onboarding.OnboardingScreen
import com.radlance.languageapp.presentation.splash.SplashScreen

/**
 * Дата создания: 25.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel = viewModel()
) {
    val onboardingState by onboardingViewModel.onboardingState.collectAsState()

    NavHost(navController = navHostController, startDestination = Splash, modifier = modifier) {
        composable<Splash> {
            SplashScreen(navigateToSplashScreen = { navHostController.navigate(Onboarding) })
        }

        composable<Onboarding> {
            OnboardingScreen(
                onboardingState = onboardingState,
                onNextClick = onboardingViewModel::moveToNextScreen,
                onChooseLanguageClick = { navHostController.navigate(ChooseLanguage) },
                onSkipClick = { navHostController.navigate(ChooseLanguage) }
            )
        }

        composable<ChooseLanguage> {
            ChooseLanguageScreen()
        }
    }
}