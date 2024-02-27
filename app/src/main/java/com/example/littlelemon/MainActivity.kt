package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.datastore.UserPreferences
import com.example.littlelemon.detail.DishDetails
import com.example.littlelemon.home.ui.HomeViewModel
import com.example.littlelemon.home.ui.compose.HomeBodyScreenState
import com.example.littlelemon.home.ui.compose.HomeScreen
import com.example.littlelemon.nav.Home
import com.example.littlelemon.nav.Onboarding
import com.example.littlelemon.nav.Profile
import com.example.littlelemon.onboarding.ui.OnboardingViewModel
import com.example.littlelemon.onboarding.ui.compose.OnboardingScreen
import com.example.littlelemon.onboarding.ui.compose.RegistrationFormState
import com.example.littlelemon.profile.ui.ProfileViewModel
import com.example.littlelemon.profile.ui.compose.ProfileScreen
import com.example.littlelemon.profile.ui.compose.ProfileScreenState
import com.example.littlelemon.ui.theme.LittleLemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Profile.route) {
                    onboardingScreen(navController)
                    homeScreen(navController)
                    dishDetailScreen(navController)
                    profileScreen(navController)
                }
            }
        }
    }

    private fun NavGraphBuilder.dishDetailScreen(navController: NavController) {
        composable(
            com.example.littlelemon.nav.DishDetails.route + "/{${com.example.littlelemon.nav.DishDetails.argDishId}}",
            arguments = listOf(navArgument(com.example.littlelemon.nav.DishDetails.argDishId) {
                type = NavType.IntType
            })
        ) {
            val id =
                requireNotNull(it.arguments?.getInt(com.example.littlelemon.nav.DishDetails.argDishId)) { "Dish id is null" }
            DishDetails(id, navController)
        }
    }

    private fun NavGraphBuilder.profileScreen(navController: NavHostController) {
        composable(Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
            val profileData = profileViewModel.profileData.collectAsState(
                initial = UserPreferences("", "", "")
            )
            val context = LocalContext.current
            LaunchedEffect(key1 = context) {
                profileViewModel.userInputEvent.collect { event ->
                    when (event) {
                        is ProfileViewModel.UserInputEvent.Logout -> {
                            navController.navigate(Onboarding.route) {
                                popUpTo(Onboarding.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
            ProfileScreen(
                profileScreenState = ProfileScreenState(
                    firstName = profileData.value.firstName,
                    lastName = profileData.value.lastName,
                    email = profileData.value.email
                )
            )
        }
    }

    private fun NavGraphBuilder.onboardingScreen(navController: NavHostController) {
        composable(Onboarding.route) {
            val onboardingViewModel = hiltViewModel<OnboardingViewModel>()
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

    private fun NavGraphBuilder.homeScreen(navController: NavHostController) {
        composable(Home.route) {
            val viewModel = viewModel<HomeViewModel>()
            val searchText by viewModel.searchText.collectAsState()
            val categories by viewModel.categories.collectAsState()
            val dishes by viewModel.dishes.collectAsState()
            val isSearching by viewModel.isSearching.collectAsState()
            HomeScreen(
                navController = navController,
                homeBodyScreenState = HomeBodyScreenState(
                    searchText = searchText,
                    onSearchTextChange = {
                        viewModel.onSearchTextChange(it)
                    },
                    isSearching = isSearching,
                    dishes = dishes,
                    categories = categories,
                    onCategorySelected = { selectedCategory ->
                        viewModel.onCategorySelected(selectedCategory)
                    }
                )
            )
        }
    }
}
