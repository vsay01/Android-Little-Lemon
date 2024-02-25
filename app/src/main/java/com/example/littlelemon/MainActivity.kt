package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.detail.DishDetails
import com.example.littlelemon.home.HomeScreen
import com.example.littlelemon.nav.Home
import com.example.littlelemon.nav.Onboarding
import com.example.littlelemon.onboarding.ui.OnboardingViewModel
import com.example.littlelemon.onboarding.ui.compose.OnboardingScreen
import com.example.littlelemon.onboarding.ui.compose.RegistrationFormState
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Home.route) {
                    onboardingScreen(navController)
                    homeScreen(navController)
                    dishDetailScreen()
                }
            }
        }
    }

    private fun NavGraphBuilder.dishDetailScreen() {
        composable(
            com.example.littlelemon.nav.DishDetails.route + "/{${com.example.littlelemon.nav.DishDetails.argDishId}}",
            arguments = listOf(navArgument(com.example.littlelemon.nav.DishDetails.argDishId) {
                type = NavType.IntType
            })
        ) {
            val id =
                requireNotNull(it.arguments?.getInt(com.example.littlelemon.nav.DishDetails.argDishId)) { "Dish id is null" }
            DishDetails(id)
        }
    }

    private fun NavGraphBuilder.homeScreen(navController: NavHostController) {
        composable(Home.route) {
            HomeScreen(navController = navController)
        }
    }

    private fun NavGraphBuilder.onboardingScreen(navController: NavHostController) {
        composable(Onboarding.route) {
            val onboardingViewModel = viewModel<OnboardingViewModel>()
            val state = onboardingViewModel.state
            val context = LocalContext.current
            LaunchedEffect(key1 = context) {
                onboardingViewModel.validationEvents.collect { event ->
                    when (event) {
                        is OnboardingViewModel.ValidationEvent.Success -> {
                            navController.navigate(Home.route)
                        }
                    }
                }
            }
            OnboardingScreen(
                registrationFormState = RegistrationFormState(
                    firstName = state.firstName,
                    firstNameError = state.firstNameError,
                    onFirstNameValueChange = {
                        onboardingViewModel.onEvent(
                            OnboardingViewModel.RegistrationFormEvent.FirstNameChanged(
                                it
                            )
                        )
                    },
                    lastName = state.lastName,
                    lastNameError = state.lastNameError,
                    onLastNameValueChange = {
                        onboardingViewModel.onEvent(
                            OnboardingViewModel.RegistrationFormEvent.LastNameChanged(
                                it
                            )
                        )
                    },
                    email = state.email,
                    emailError = state.emailError,
                    onEmailValueChange = {
                        onboardingViewModel.onEvent(
                            OnboardingViewModel.RegistrationFormEvent.EmailChanged(
                                it
                            )
                        )
                    },
                    onSubmit = {
                        onboardingViewModel.onEvent(
                            OnboardingViewModel.RegistrationFormEvent.Submit
                        )
                    }
                )
            )
        }
    }
}
