package com.radlance.languageapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.radlance.languageapp.presentation.auth.signin.SignInScreen
import com.radlance.languageapp.presentation.auth.signup.FirstSignUpScreen
import com.radlance.languageapp.presentation.auth.signup.LastSignUpScreen
import com.radlance.languageapp.presentation.language.ChooseLanguageScreen
import com.radlance.languageapp.presentation.main.MainScreen
import com.radlance.languageapp.presentation.onboarding.OnboardingScreen
import com.radlance.languageapp.presentation.profile.ProfileScreen
import com.radlance.languageapp.presentation.profile.ProfileViewModel
import com.radlance.languageapp.presentation.profile.ResizePictureScreen
import com.radlance.languageapp.presentation.splash.SplashScreen

/**
 * Дата создания: 25.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel = viewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val currentImage by profileViewModel.currentImage.collectAsState()

    val onboardingState by navigationViewModel.onboardingState.collectAsState()
    val onboardingViewed by navigationViewModel.onboardingViewed.collectAsState()

    var closeCurrentScreen: () -> Unit = {
        navHostController.navigate(SignIn)
    }

    NavHost(navController = navHostController, startDestination = Main, modifier = modifier) {
        composable<Splash> {
            SplashScreen(
                navigateToSplashScreen = {
                    if (onboardingViewed) {
                        navHostController.navigate(ChooseLanguage)
                    } else {
                        navHostController.navigate(Onboarding) {
                            popUpTo<Splash> {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        composable<Onboarding> {
            onboardingState?.let { state ->
                OnboardingScreen(
                    onboardingState = state,
                    onNextClick = navigationViewModel::moveToNextScreen,
                    onChooseLanguageClick = {
                        navHostController.navigate(ChooseLanguage)
                        navigationViewModel.saveOnboardingViewed()

                    },
                    onSkipClick = {
                        navHostController.navigate(ChooseLanguage)
                        navigationViewModel.saveOnboardingViewed()
                    }
                )
            }
        }

        composable<ChooseLanguage> {
            ChooseLanguageScreen(
                closeCurrentScreen = closeCurrentScreen,
                setDefaultCloseScreen = {
                    closeCurrentScreen = { navHostController.navigate(SignIn) }
                }
            )
        }

        composable<SignIn> {
            SignInScreen(
                navigateToSignUp = { navHostController.navigate(FirstSignUp) },
                onBackPressed = navHostController::navigateUp,
                navigateToHomeScreen = {
                    navHostController.navigate(Main) {
                        popUpTo<ChooseLanguage> { inclusive = false }
                    }
                }
            )
        }

        composable<FirstSignUp> {
            FirstSignUpScreen(
                navigateToSignIn = { navHostController.navigate(SignIn) },
                navigateToLastSignUp = { firstName, lastName, email ->
                    navHostController.navigate(LastSignUp(firstName, lastName, email))
                },
                onBackPressed = navHostController::navigateUp
            )
        }

        composable<LastSignUp> {
            val args = it.toRoute<LastSignUp>()

            LastSignUpScreen(
                onBackPressed = navHostController::navigateUp,
                navigateToSignIn = { navHostController.navigate(SignIn) },
                firstName = args.firstName,
                lastName = args.lastName,
                email = args.email,
                navigateToHomeScreen = {
                    navHostController.navigate(Main) {
                        popUpTo<ChooseLanguage> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Main> {
            MainScreen(navigateToProfile = { navHostController.navigate(Profile) })
        }

        composable<Profile> {
            ProfileScreen(
                navigateToChooseLanguage = {
                    closeCurrentScreen = { navHostController.navigateUp() }
                    navHostController.navigate(ChooseLanguage)
                },
                navigateToResizePictureImage = { navHostController.navigate(ResizePicture) },
                profileViewModel = profileViewModel
            )
        }

        composable<ResizePicture> {
            ResizePictureScreen(
                navigateUp = navHostController::navigateUp,
                image = currentImage,
                profileViewModel = profileViewModel
            )
        }
    }
}