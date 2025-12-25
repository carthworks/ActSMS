package com.actsms.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.actsms.app.presentation.screens.dashboard.DashboardScreen
import com.actsms.app.presentation.screens.onboarding.OnboardingScreen

/**
 * Navigation routes for the app
 */
sealed class Screen(val route: String) {
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
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToActionDetail = { actionId ->
                    navController.navigate(Screen.ActionDetail.createRoute(actionId))
                }
            )
        }

        // Add other screens as they are implemented
    }
}
