package com.actsms.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.actsms.app.presentation.screens.dashboard.DashboardScreen
import com.actsms.app.presentation.screens.onboarding.OnboardingScreen
import com.actsms.app.presentation.screens.settings.SettingsScreen
import com.actsms.app.presentation.screens.welcome.WelcomeScreen

/**
 * Navigation routes for the app
 */
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Onboarding : Screen("onboarding")
    object Dashboard : Screen("dashboard")
    object ActionDetail : Screen("action_detail/{actionId}") {
        fun createRoute(actionId: String) = "action_detail/$actionId"
    }
    object Settings : Screen("settings")
}

/**
 * Main navigation component
 */
@Composable
fun ActSmsNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        // Welcome Screen - First screen
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStarted = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        // Onboarding Screen - Permission request
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Dashboard Screen - Main app screen
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToWelcome = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToActionDetail = { actionId ->
                    navController.navigate(Screen.ActionDetail.createRoute(actionId))
                }
            )
        }

        // Settings Screen
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Add other screens as they are implemented
    }
}
