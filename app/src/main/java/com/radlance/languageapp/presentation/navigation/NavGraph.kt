package com.radlance.languageapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.radlance.languageapp.presentation.onboarding.OnboardingScreen
import com.radlance.languageapp.presentation.splash.SplashScreen

/**
 * Дата создания: 25.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navHostController, startDestination = Splash, modifier = modifier) {
        composable<Splash> {
            SplashScreen(navigateToSplashScreen = { navHostController.navigate(Onboarding) })
        }

        composable<Onboarding> {
            OnboardingScreen()
        }
    }
}